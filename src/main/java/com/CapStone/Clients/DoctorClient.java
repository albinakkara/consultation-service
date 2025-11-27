package com.CapStone.Clients;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "doctor-service")
public interface DoctorClient {

    @GetMapping("internal/doctors/{id}")
    public ResponseEntity<Boolean> validateDoctorWithId(@PathVariable("id") Long id);
}
