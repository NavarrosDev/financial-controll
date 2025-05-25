package com.navarro.financial.controll.authentication.dto.login;

public record LoginResponse(String username, String accessToken, Long expiresIn) {
}
