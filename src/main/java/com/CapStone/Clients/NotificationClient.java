package com.CapStone.Clients;

import com.CapStone.DTO.ConsultationStatusNotificationRequest;
import com.CapStone.DTO.DoctorConsultationAlertRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service")
public interface NotificationClient {
    @PostMapping("/notifications/doctor/new-consultation")
    public String sendDoctorAlert(@RequestBody DoctorConsultationAlertRequest req);

    @PostMapping("/notifications/patient/consultation-status")
    public String sendConsultationStatus(@RequestBody ConsultationStatusNotificationRequest req);
}
