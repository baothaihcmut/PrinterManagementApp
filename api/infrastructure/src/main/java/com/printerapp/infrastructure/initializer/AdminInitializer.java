package com.printerapp.infrastructure.initializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.printerapp.domain.aggregates.user.User;
import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.enums.Role;
import com.printerapp.domain.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {
    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.phoneNumber}")
    private String adminPhoneNumber;

    @Value("${admin.firstName}")
    private String adminFirstName;

    @Value("${admin.lastName}")
    private String adminLastName;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Email email = new Email(adminEmail, Role.ADMIN);
        User user = this.userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            User admin = new User(new UserName(adminFirstName, adminLastName), adminPhoneNumber, email, Role.ADMIN,
                    null, null);
            this.userRepository.save(admin);
        }

    }

}
