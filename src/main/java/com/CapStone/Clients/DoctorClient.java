package com.CapStone.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "doctor-service")
public interface DoctorClient {

    @GetMapping("internal/doctors/validate/{id}")
    public ResponseEntity<Boolean> validateDoctorWithId(@PathVariable("id") Long id);

    @GetMapping("internal/doctors/email/{id}")
    public ResponseEntity<String> getDoctorEmailById(@PathVariable("id") Long id);
}
