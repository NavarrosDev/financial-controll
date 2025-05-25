package com.navarro.financial.controll.authentication.controller;

import com.navarro.financial.controll.authentication.dto.DeleteResponse;
import com.navarro.financial.controll.authentication.dto.login.LoginRequest;
import com.navarro.financial.controll.authentication.dto.login.LoginResponse;
import com.navarro.financial.controll.authentication.dto.register.RegisterRequest;
import com.navarro.financial.controll.authentication.dto.register.RegisterResponse;
import com.navarro.financial.controll.authentication.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
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

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeleteResponse> delete(@PathVariable String username) {
        authenticationService.delete(username);
        var body = new DeleteResponse(String.format("User %s deleted successfully!", username));
        return ResponseEntity.ok(body);
    }
}