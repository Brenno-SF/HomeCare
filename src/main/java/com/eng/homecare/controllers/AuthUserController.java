package com.eng.homecare.controllers;

import com.eng.homecare.request.LoginUserRequestDTO;
import com.eng.homecare.services.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequestMapping("login")
public class AuthUserController {
    @Autowired
    private AuthUserService authUserService;

    @PostMapping("user")
    public ResponseEntity<String> create(@RequestBody LoginUserRequestDTO loginUserRequestDTO){
        try {
            var result = this.authUserService.execute(loginUserRequestDTO);
            return ResponseEntity.ok().body(result);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
