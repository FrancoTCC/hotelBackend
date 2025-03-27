package com.francode.hotelBackend.persistence.DataSeeders;

import com.francode.hotelBackend.domain.entity.Company;
import com.francode.hotelBackend.domain.entity.ERole;
import com.francode.hotelBackend.domain.entity.Role;
import com.francode.hotelBackend.domain.entity.UserApp;
import com.francode.hotelBackend.persistence.repository.JpaCompanyRepository;
import com.francode.hotelBackend.persistence.repository.JpaUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private final JpaUserRepository userRepository;
    private final JpaCompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(JpaUserRepository userRepository, JpaCompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear usuario admin
        if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName(ERole.ROLE_ADMIN);

            Set<Role> roles = Collections.singleton(adminRole);

            UserApp adminUser = new UserApp();
            adminUser.setEmail("admin@gmail.com");
            adminUser.setPassword(passwordEncoder.encode("123456"));
            adminUser.setRoles(roles);

            userRepository.save(adminUser);
            System.out.println("Usuario admin creado exitosamente.");
        } else {
            System.out.println("El usuario admin ya existe.");
        }

        // Crear empresa
        if (companyRepository.findById(1L).isEmpty()) {
            Company company = new Company();
            company.setName("Formula 1");
            company.setEmail("formula1@f1.com");
            company.setRuc("12345678901");
            company.setLocation("Ubicación de la empresa");
            company.setPhone("963265986");
            company.setLogoUrl("http://example.com/logo.png");

            companyRepository.save(company);
            System.out.println("Empresa creada exitosamente.");
        } else {
            System.out.println("La empresa ya existe.");
        }
    }
}
