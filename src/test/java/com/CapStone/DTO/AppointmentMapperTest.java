package com.CapStone.DTO;

import com.CapStone.Entity.Appointment;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentMapperTest {

    @Test
    void mapToDTO_copiesFieldsCorrectly() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1L);
        appointment.setAppointmentDate(LocalDate.of(2025, 1, 1));
        appointment.setAppointmentSlot(LocalTime.of(10, 30));
        appointment.setReason("Regular checkup");
        appointment.setStatus("PENDING");
        appointment.setDoctorId(2L);
        appointment.setPatientId(3L);

        AppointmentDTO dto = AppointmentMapper.mapToDTO(appointment);

        assertEquals(appointment.getAppointmentId(), dto.getAppointmentId());
        assertEquals(appointment.getAppointmentDate(), dto.getAppointmentDate());
        assertEquals(appointment.getAppointmentSlot(), dto.getAppointmentSlot());
        assertEquals(appointment.getReason(), dto.getReason());
        assertEquals(appointment.getStatus(), dto.getStatus());
        assertEquals(appointment.getDoctorId(), dto.getDoctorId());
        assertEquals(appointment.getPatientId(), dto.getPatientId());
    }

    @Test
    void copyToEntity_updatesEntityFromDto() {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setAppointmentDate(LocalDate.of(2025, 2, 2));
        dto.setAppointmentSlot(LocalTime.of(14, 0));
        dto.setReason("Follow-up");
        dto.setStatus("CONFIRMED");
        dto.setDoctorId(5L);
        dto.setPatientId(6L);

        Appointment appointment = new Appointment();

        AppointmentMapper.copyToEntity(dto, appointment);

        assertEquals(dto.getAppointmentDate(), appointment.getAppointmentDate());
        assertEquals(dto.getAppointmentSlot(), appointment.getAppointmentSlot());
        assertEquals(dto.getReason(), appointment.getReason());
        assertEquals(dto.getStatus(), appointment.getStatus());
        assertEquals(dto.getDoctorId(), appointment.getDoctorId());
        assertEquals(dto.getPatientId(), appointment.getPatientId());
    }
}
