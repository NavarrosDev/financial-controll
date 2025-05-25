package com.navarro.financial.controll.authentication.services;

import com.navarro.financial.controll.authentication.dto.login.LoginRequest;
import com.navarro.financial.controll.authentication.dto.login.LoginResponse;
import com.navarro.financial.controll.authentication.dto.register.RegisterRequest;
import com.navarro.financial.controll.authentication.dto.register.RegisterResponse;

public interface AuthenticationService {
    LoginResponse login(LoginRequest request);
    RegisterResponse register(RegisterRequest request);
    void delete(String username);
}
