
package com.CapStone.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AppointmentId;

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private Long doctorId;

    private String reason;

    @Column(nullable = false)
    private LocalDate appointmentDate;

    @Column(nullable = false)
    private LocalTime appointmentSlot; // Example: 10:00–10:30

    @Column(nullable = false)
    private String status = "Scheduled"; // Scheduled / Completed / Cancelled

    public Appointment() {}

    // -------- Getters & Setters --------
    public Long getAppointmentId() { return AppointmentId; }
    public void setAppointmentId(Long id) { this.AppointmentId = AppointmentId; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public LocalTime getAppointmentSlot() { return appointmentSlot; }
    public void setAppointmentSlot(LocalTime appointmentSlot) { this.appointmentSlot = appointmentSlot; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
