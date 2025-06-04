package com.eng.homecare.controllers;

import com.eng.homecare.entities.Professional;
import com.eng.homecare.request.PatientRequestDTO;
import com.eng.homecare.request.ProfessionalRequestDTO;
import com.eng.homecare.response.PatientResponseDTO;
import com.eng.homecare.response.ProfessionalResponseDTO;
import com.eng.homecare.services.ProfessionalServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("professional")
public class ProfessionalController {
    @Autowired
    private ProfessionalServices professionalServices;

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
    public ResponseEntity<String> deleteAllPatient(){
        professionalServices.removeAll();
        return ResponseEntity.ok("All professionals has been deleted.");
    }
}
