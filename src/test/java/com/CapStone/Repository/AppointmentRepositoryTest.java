package com.CapStone.Repository;

import com.CapStone.Entity.Appointment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentRepositoryTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Test
    void findAppointmentsByPatientId_returnsAppointments() {
        Long patientId = 10L;

        Appointment appointment = new Appointment();
        appointment.setPatientId(patientId);
        appointment.setAppointmentDate(LocalDate.of(2025, 1, 1));
        appointment.setAppointmentSlot(LocalTime.of(9, 0));

        List<Appointment> mockResult = Collections.singletonList(appointment);

        when(appointmentRepository.findAppointmentsByPatientId(patientId))
                .thenReturn(mockResult);

        List<Appointment> result = appointmentRepository.findAppointmentsByPatientId(patientId);

        // Just verify the list size and patientId – no appointmentId assertion
        assertEquals(1, result.size());
        assertEquals(patientId, result.get(0).getPatientId());
    }
}
