package com.navarro.financial.controll.authentication.services;

import com.navarro.financial.controll.authentication.dto.login.LoginRequest;
import com.navarro.financial.controll.authentication.dto.login.LoginResponse;
import com.navarro.financial.controll.authentication.dto.register.RegisterRequest;
import com.navarro.financial.controll.authentication.dto.register.RegisterResponse;
import com.navarro.financial.controll.authentication.entities.User;
import com.navarro.financial.controll.authentication.repositories.RoleRepository;
import com.navarro.financial.controll.authentication.repositories.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    public final JwtEncoder jwtEncoder;
    public final UserRepository userRepository;
    public final BCryptPasswordEncoder encoder;
    public final RoleRepository roleRepository;

    public AuthenticationServiceImpl(JwtEncoder jwtEncoder,
                                     UserRepository userRepository,
                                     BCryptPasswordEncoder encoder,
                                     RoleRepository roleRepository) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Optional<User> user = this.userRepository.findByUsername(request.username());

        if (user.isEmpty() || !user.get().isLoginCorrect(request, this.encoder)) {
            throw new BadCredentialsException("Username or password is invalid!");
        }

        var expiresIn = 300L;
        var jwtValue = this.createTokenJwt(user.get(), expiresIn);
        return new LoginResponse(request.username(), jwtValue, expiresIn);
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        this.userRepository.findByUsername(request.username().toUpperCase())
                .ifPresent((user) -> {
                    throw new BadCredentialsException(String.format("User %s already exist!", user.getUsername()));
                });
        var role = this.roleRepository.findByName(request.role());

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(encoder.encode(request.password()));
        user.setRoles(Set.of(role));
        this.userRepository.save(user);

        var expiresIn = 300L;
        var jwtValue = this.createTokenJwt(user, expiresIn);
        return new RegisterResponse(request.username(), jwtValue, expiresIn);
    }

    private String createTokenJwt(User user, Long expiresIn) {
        var now = Instant.now();
        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.getUserId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
