package com.CapStone.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CapStone.Entity.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.patientId = :id")
    List<Appointment> findAppointmentsByPatientId(@Param("id") Long id);

}
