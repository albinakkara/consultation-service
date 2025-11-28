package com.CapStone.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorConsultationAlertRequest {
    private Long consultationId;
    private String doctorEmail;
}
