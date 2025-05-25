package com.navarro.financial.controll.authentication.config;

import com.navarro.financial.controll.authentication.entities.Role;
import com.navarro.financial.controll.authentication.entities.User;
import com.navarro.financial.controll.authentication.repositories.RoleRepository;
import com.navarro.financial.controll.authentication.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

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
        var roleAdmin = this.roleRepository.findByName(Role.Values.ADMIN.name());
        var userAdmin = this.userRepository.findByUsername("Admin");

        userAdmin.ifPresentOrElse(
                (user) -> System.out.printf("User %s already exist!", user.getUsername()),
                () -> {
                    var user = new User();
                    user.setUsername("Admin");
                    user.setPassword(encoder.encode("123"));
                    user.setRoles(Set.of(roleAdmin));
                    this.userRepository.save(user);
                }
        );
    }
}
