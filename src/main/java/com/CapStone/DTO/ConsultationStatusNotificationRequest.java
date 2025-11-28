package com.CapStone.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConsultationStatusNotificationRequest {
    private Long consultationId;
    private String patientEmailId;
    private String status;
}
