package com.CapStone.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppointmentListRequest {
    @NotNull(message = "patient id is required")
    private Long patientId;
}
