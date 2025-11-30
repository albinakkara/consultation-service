package com.CapStone.Controller;

import com.CapStone.Clients.DoctorClient;
import com.CapStone.Clients.NotificationClient;
import com.CapStone.Clients.PatientClient;
import com.CapStone.DTO.*;
import com.CapStone.Entity.Appointment;
import com.CapStone.Repository.AppointmentRepository;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/consultations")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;
    private final NotificationClient notificationClient;

    public AppointmentController(AppointmentRepository appointmentRepository,
                                 PatientClient patientClient,
                                 DoctorClient doctorClient, NotificationClient notificationClient) {
        this.appointmentRepository = appointmentRepository;
        this.patientClient = patientClient;
        this.doctorClient = doctorClient;
        this.notificationClient = notificationClient;
    }


    @GetMapping
    public ResponseEntity<List<AppointmentDetailsDTO>> getAllAppointments(@RequestParam Long patientId) {
        List<Appointment> appointments = appointmentRepository.findAppointmentsByPatientId(patientId);

        List<AppointmentDetailsDTO> enrichedAppointments = appointments.stream()
                .map(appointment -> {
                    SkeletonDoctorDto doctor = doctorClient.getDoctorDetailsById(appointment.getDoctorId()).getBody();
                    return new AppointmentDetailsDTO(appointment, doctor);
                })
                .toList();

        return ResponseEntity.ok(enrichedAppointments);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));
        return ResponseEntity.ok(appointment);
    }

    @PostMapping
    public ResponseEntity<Long> createAppointment(@Valid @RequestBody AppointmentDTO dto) {

        assertPatientExists(dto.getPatientId());
        assertDoctorExists(dto.getDoctorId());

        Appointment appointment = AppointmentMapper.mapToEntity(dto);
        Appointment saved = appointmentRepository.save(appointment);

        notifyDoctor(saved);

        return ResponseEntity.ok(saved.getAppointmentId());
    }

    private void notifyDoctor(Appointment appointment) {
        ResponseEntity<String> doctorEmailResponse = doctorClient.getDoctorEmailById(appointment.getDoctorId());
        if(doctorEmailResponse.getStatusCode().isError()){
            throw new RuntimeException("Email service unavailable");
        }
        DoctorConsultationAlertRequest req = new DoctorConsultationAlertRequest(appointment.getAppointmentId(), doctorEmailResponse.getBody().toLowerCase(Locale.ROOT));
        notificationClient.sendDoctorAlert(req);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Boolean> updateAppointmentStatus(@PathVariable("id") Long id, @RequestBody AppointmentStatusUpdateRequest request){
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()->new RuntimeException("Appointment not found!"));
        appointment.setStatus(request.getStatus());
        appointmentRepository.save(appointment);
        notifyPatient(appointment);

        return ResponseEntity.ok(true);
    }

    private void notifyPatient(Appointment appointment) {
        ResponseEntity<String> patientEmailResponse = patientClient.getPatientEmailById(appointment.getPatientId());
        if(patientEmailResponse.getStatusCode().isError()){
            throw new RuntimeException("Email service unavailable");
        }
        ConsultationStatusNotificationRequest req = new ConsultationStatusNotificationRequest(appointment.getAppointmentId(), patientEmailResponse.getBody().toLowerCase(Locale.ROOT), appointment.getStatus());
        notificationClient.sendConsultationStatus(req);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateAppointment(@PathVariable Long id, @RequestBody AppointmentDTO dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));

//        // If client updates doctor/patient IDs, validate them (optional — enforce business rules as needed)
        if (dto.getPatientId() != null) {
            assertPatientExists(dto.getPatientId());
        }
        if (dto.getDoctorId() != null) {
            assertDoctorExists(dto.getDoctorId());
        }

        // Copy fields and save
        AppointmentMapper.copyToEntity(dto, appointment);
        Appointment updated = appointmentRepository.save(appointment);

        return ResponseEntity.ok(updated.getAppointmentId());
    }

    @DeleteMapping("/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentRepository.deleteById(id);
        return "Appointment deleted successfully";
    }


    private void assertPatientExists(Long patientId) {
        try {
            patientClient.validatePatientWithId(patientId);
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Patient not found");
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Patient service unavailable", e);
        }
    }

    private void assertDoctorExists(Long doctorId) {
        try {
            doctorClient.validateDoctorWithId(doctorId);
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Doctor not found");
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Doctor service unavailable", e);
        }
    }
}
