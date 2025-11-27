
package com.CapStone.DTO;

import com.CapStone.Entity.Appointment;

public class AppointmentMapper {

    public static AppointmentDTO mapToDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setAppointmentSlot(appointment.getAppointmentSlot());
        dto.setReason(appointment.getReason());
        dto.setStatus(appointment.getStatus());
        dto.setDoctorId(appointment.getDoctorId());
        dto.setPatientId(appointment.getPatientId());
        return dto;
    }

    public static Appointment mapToEntity(AppointmentDTO dto) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(dto.getAppointmentId());
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentSlot(dto.getAppointmentSlot());
        appointment.setReason(dto.getReason());
        appointment.setStatus(dto.getStatus());
        appointment.setDoctorId(dto.getDoctorId());
        appointment.setPatientId(dto.getPatientId());
        return appointment;
    }

    public static void copyToEntity(AppointmentDTO dto, Appointment appointment) {
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentSlot(dto.getAppointmentSlot());
        appointment.setReason(dto.getReason());
        appointment.setStatus(dto.getStatus());
        appointment.setDoctorId(dto.getDoctorId());
        appointment.setPatientId(dto.getPatientId());
    }
}
