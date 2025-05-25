package com.navarro.financial.controll.authentication.dto.register;

import com.navarro.financial.controll.authentication.entities.Role;

public record RegisterRequest(String username, String password, String role) {
}
