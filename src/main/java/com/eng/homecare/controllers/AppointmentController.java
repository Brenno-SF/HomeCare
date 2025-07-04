package com.eng.homecare.controllers;

import com.eng.homecare.enums.AppointmentStatus;
import com.eng.homecare.request.AppointmentRequestDTO;
import com.eng.homecare.request.AppointmentStatusDTO;
import com.eng.homecare.response.AppointmentResponseDTO;
import com.eng.homecare.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping()
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@RequestBody AppointmentRequestDTO appointmentRequestDTO){
        return ResponseEntity.ok(appointmentService.createAppointment(appointmentRequestDTO));
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<AppointmentResponseDTO> updateStatusAppointment(@PathVariable String id, @RequestBody AppointmentStatusDTO appointmentStatus){
        AppointmentStatus status = AppointmentStatus.valueOf(appointmentStatus.status().toUpperCase());
        return ResponseEntity.ok(appointmentService.updateStatus(id, status));
    }
}
