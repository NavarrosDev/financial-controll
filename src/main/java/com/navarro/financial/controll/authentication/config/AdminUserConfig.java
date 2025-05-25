package com.navarro.financial.controll.authentication.config;

import com.navarro.financial.controll.authentication.entities.Role;
import com.navarro.financial.controll.authentication.entities.User;
import com.navarro.financial.controll.authentication.repositories.RoleRepository;
import com.navarro.financial.controll.authentication.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminUserConfig.class);
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public AdminUserConfig(RoleRepository roleRepository,
                           UserRepository userRepository,
                           BCryptPasswordEncoder encoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        this.createAdminUserIfNotExists();
    }

    private void createAdminUserIfNotExists() {
        String adminUsername = "Admin";
        String defaultPassword = "123";

        this.userRepository.findByUsername(adminUsername).ifPresentOrElse(
                user -> log.info("User '{}' already exists!", user.getUsername()),
                () -> {
                    Set<Role> allRoles = new HashSet<>(this.roleRepository.findAll());
                    User user = new User();
                    user.setUsername(adminUsername);
                    user.setPassword(encoder.encode(defaultPassword));
                    user.setRoles(allRoles);
                    this.userRepository.save(user);
                    log.info("Admin user '{}' created successfully.", adminUsername);
                }
        );
    }
}
