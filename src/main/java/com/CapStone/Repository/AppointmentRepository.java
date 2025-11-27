package com.CapStone.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CapStone.Entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {}
