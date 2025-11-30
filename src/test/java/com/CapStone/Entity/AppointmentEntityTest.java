package com.CapStone.Entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentEntityTest {

    @Test
    void appointment_gettersAndSetters_workCorrectly() {
        Appointment appointment = new Appointment();

        String reason = "Routine checkup";
        LocalDate date = LocalDate.of(2025, 1, 10);
        LocalTime slot = LocalTime.of(10, 30);
        String status = "PENDING";

        appointment.setReason(reason);
        appointment.setAppointmentDate(date);
        appointment.setAppointmentSlot(slot);
        appointment.setStatus(status);

        assertEquals(reason, appointment.getReason());
        assertEquals(date, appointment.getAppointmentDate());
        assertEquals(slot, appointment.getAppointmentSlot());
        assertEquals(status, appointment.getStatus());
    }
}
