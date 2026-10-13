package com.example.healthcare_appointment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final List<Appointment> appointments =
            new ArrayList<>();

    private final AtomicLong counter = new AtomicLong();

    @GetMapping
    public List<Appointment> getAppointments() {
        return appointments;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment createAppointment(
            @RequestBody Appointment appointment) {

        if (appointment.getPatientName() == null
                || appointment.getPatientName().isBlank()
                || appointment.getDoctorName() == null
                || appointment.getDoctorName().isBlank()
                || appointment.getDate() == null
                || appointment.getDate().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Patient, doctor and date are required");
        }

        appointment.setId(counter.incrementAndGet());
        appointments.add(appointment);

        return appointment;
    }

    @GetMapping("/{id}")
    public Appointment getAppointment(@PathVariable Long id) {
        return appointments.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Appointment not found"));
    }
}