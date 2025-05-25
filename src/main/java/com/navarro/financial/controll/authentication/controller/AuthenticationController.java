package com.navarro.financial.controll.authentication.controller;

import com.navarro.financial.controll.authentication.dto.login.LoginRequest;
import com.navarro.financial.controll.authentication.dto.login.LoginResponse;
import com.navarro.financial.controll.authentication.dto.register.RegisterRequest;
import com.navarro.financial.controll.authentication.dto.register.RegisterResponse;
import com.navarro.financial.controll.authentication.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/authentication")
public class AuthenticationController {

   private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(this.authenticationService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.authenticationService.register(request));
    }
}
