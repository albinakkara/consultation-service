package com.CapStone.DTO;

import com.CapStone.Entity.Appointment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDetailsDTO {

    private Long appointmentId;
    private String reason;
    private LocalDate appointmentDate;
    private LocalTime appointmentSlot;
    private String status;
    private String priority;

    private String doctorFirstName;
    private String doctorLastName;
    private String doctorEmail;
    private String doctorSpecialisation;

    public AppointmentDetailsDTO(Appointment appointment, SkeletonDoctorDto doctor) {
        this.appointmentId = appointment.getAppointmentId();
        this.reason = appointment.getReason();
        this.appointmentDate = appointment.getAppointmentDate();
        this.appointmentSlot = appointment.getAppointmentSlot();
        this.status = appointment.getStatus();
        this.priority = appointment.getPriority();

        this.doctorFirstName = doctor.getFirstName();
        this.doctorLastName = doctor.getLastName();
        this.doctorEmail = doctor.getEmail();
        this.doctorSpecialisation = doctor.getSpecialisation();
    }

}
