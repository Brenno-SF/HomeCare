package com.eng.homecare.controllers;

import com.eng.homecare.request.AvailabilityRequestDTO;
import com.eng.homecare.request.ProfessionalRequestDTO;
import com.eng.homecare.response.AppointmentResponseDTO;
import com.eng.homecare.response.AvailabilityResponseDTO;
import com.eng.homecare.response.ProfessionalResponseDTO;
import com.eng.homecare.services.AppointmentService;
import com.eng.homecare.services.AvailabilityService;
import com.eng.homecare.services.ProfessionalServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("professional")
public class ProfessionalController {
    @Autowired
    private ProfessionalServices professionalServices;
    @Autowired
    private AvailabilityService availabilityService;
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("register")
    public ResponseEntity<ProfessionalResponseDTO> saveProfessional(@RequestBody ProfessionalRequestDTO professionalRequestDTO){
        ProfessionalResponseDTO professionalResponseDTO = professionalServices.create(professionalRequestDTO);
        return ResponseEntity.ok(professionalResponseDTO);
    }
    @GetMapping
    public ResponseEntity<List<ProfessionalResponseDTO>> getAllProfessional(){
        return ResponseEntity.ok(professionalServices.listAll());
    }
    @GetMapping("/{professionalId}")
    public ResponseEntity<ProfessionalResponseDTO> getProfessionalById(@PathVariable Long professionalId){
        return ResponseEntity.ok(professionalServices.listById(professionalId));
    }
    @PutMapping("/{professionalId}")
    public ResponseEntity<ProfessionalResponseDTO> updateProfessionalById(@PathVariable Long professionalId, @RequestBody ProfessionalRequestDTO professionalRequestDTO){

        return ResponseEntity.ok(professionalServices.update(professionalId,professionalRequestDTO));
    }

    @DeleteMapping("/{professionalId}")
    public ResponseEntity<String> deleteById(@PathVariable Long professionalId){
        professionalServices.removeById(professionalId);
        return ResponseEntity.ok("The professional has been successfully deleted");
    }
    @DeleteMapping
    public ResponseEntity<String> deleteAllProfessional(){
        professionalServices.removeAll();
        return ResponseEntity.ok("All professionals has been deleted.");
    }
    @PutMapping("/{professionalId}/updateAppointment")
    public ResponseEntity<AvailabilityResponseDTO> updateAvailabilityProfessional(@PathVariable Long professionalId, @RequestBody AvailabilityRequestDTO availabilityRequestDTO){
        return ResponseEntity.ok(availabilityService.updateAvailability(availabilityRequestDTO, professionalId));
    }
    //appointment
    @GetMapping("/{professionalId}/appointment")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointments(@PathVariable Long professionalId){
        return ResponseEntity.ok(appointmentService.listByProfessionalId(professionalId));
    }
}
