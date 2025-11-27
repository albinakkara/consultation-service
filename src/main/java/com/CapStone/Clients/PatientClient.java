
package com.CapStone.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "patient-service")
public interface PatientClient {

    @GetMapping("internal/patients/{id}")
    public ResponseEntity<Boolean> validatePatientWithId(@PathVariable("id") Long id);
}
