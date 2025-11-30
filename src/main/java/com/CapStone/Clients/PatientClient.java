package com.CapStone.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service")
public interface PatientClient {

    @GetMapping("internal/patients/validate/{id}")
    public ResponseEntity<Boolean> validatePatientWithId(@PathVariable("id") Long id);

    @GetMapping("internal/patients/email/{id}")
    public ResponseEntity<String> getPatientEmailById(@PathVariable("id") Long id);
}
