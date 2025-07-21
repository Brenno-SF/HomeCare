package com.eng.homecare.controllers;

import com.eng.homecare.config.JWTUserData;
import com.eng.homecare.enums.AppointmentStatus;
import com.eng.homecare.request.AppointmentRequestDTO;
import com.eng.homecare.request.AppointmentStatusDTO;
import com.eng.homecare.response.AppointmentResponseDTO;
import com.eng.homecare.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("{professionalId}/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping()
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@PathVariable Long professionalId, @RequestBody AppointmentRequestDTO dto, @AuthenticationPrincipal JWTUserData patientData){
        return ResponseEntity.ok(appointmentService.createAppointment(professionalId, patientData.id(),dto));
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<AppointmentResponseDTO> updateStatusAppointment(@PathVariable String id, @RequestBody AppointmentStatusDTO appointmentStatus){
        AppointmentStatus status = AppointmentStatus.valueOf(appointmentStatus.status().toUpperCase());
        return ResponseEntity.ok(appointmentService.updateStatus(id, status));
    }
}
