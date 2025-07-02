package com.eng.homecare.controllers;

import com.eng.homecare.request.AppointmentRequestDTO;
import com.eng.homecare.response.AppointmentResponseDTO;
import com.eng.homecare.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping()
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@RequestBody AppointmentRequestDTO appointmentRequestDTO){
        return ResponseEntity.ok(appointmentService.createAppointment(appointmentRequestDTO));
    }
}
