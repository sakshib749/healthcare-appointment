package com.example.healthcare_appointment;

import com.example.healthcare_appointment.AppointmentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppointmentController.class)
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createAppointmentSuccessfully() throws Exception {
        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "patientName": "Test Patient",
                      "doctorName": "Dr. Sharma",
                      "date": "2026-11-01"
                    }
                    """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.doctorName")
                        .value("Dr. Sharma"));
    }

    @Test
    void rejectAppointmentWithoutPatientName()
            throws Exception {
        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                      "patientName": "",
                      "doctorName": "Dr. Sharma",
                      "date": "2026-11-01"
                    }
                    """))
                .andExpect(status().isBadRequest());
    }
}