
package com.CapStone.Controller;

import com.CapStone.Clients.DoctorClient;
import com.CapStone.Clients.PatientClient;
import com.CapStone.DTO.AppointmentDTO;
import com.CapStone.DTO.AppointmentMapper;
import com.CapStone.Entity.Appointment;
import com.CapStone.Repository.AppointmentRepository;
//import com.CapStone.Clients.PatientClient;
//import com.CapStone.Clients.DoctorClient;


import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;

    public AppointmentController(AppointmentRepository appointmentRepository,
                                 PatientClient patientClient,
                                 DoctorClient doctorClient) {
        this.appointmentRepository = appointmentRepository;
        this.patientClient = patientClient;
        this.doctorClient = doctorClient;
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(appointmentRepository.findAll());
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

        return ResponseEntity.ok(saved.getAppointmentId());
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
