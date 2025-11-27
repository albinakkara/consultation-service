package com.CapStone.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentDTO {

    private Long AppointmentId;
    private LocalDate appointmentDate;
    private LocalTime appointmentSlot;
    private String reason;
    private String status;

    @NotNull(message = "doctor id is required")
    private Long doctorId;


    @NotNull(message = "patient id is required")
    private Long patientId;
    

}
