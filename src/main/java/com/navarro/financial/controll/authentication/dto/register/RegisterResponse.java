package com.navarro.financial.controll.authentication.dto.register;

public record RegisterResponse(String username, String accessToken, Long expiresIn) {
}
