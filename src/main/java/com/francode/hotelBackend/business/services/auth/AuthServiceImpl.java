package com.francode.hotelBackend.business.services.auth;

import com.francode.hotelBackend.domain.entity.ERole;
import com.francode.hotelBackend.domain.entity.Employee;
import com.francode.hotelBackend.domain.entity.Role;
import com.francode.hotelBackend.domain.entity.UserApp;
import com.francode.hotelBackend.exceptions.custom.ValidationException;
import com.francode.hotelBackend.persistence.repository.JpaEmployeeRepository;
import com.francode.hotelBackend.persistence.repository.JpaUserRepository;
import com.francode.hotelBackend.presentation.dto.auth.JwtResponse;
import com.francode.hotelBackend.presentation.dto.auth.LoginRequest;
import com.francode.hotelBackend.presentation.dto.auth.SignupRequest;
import com.francode.hotelBackend.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final JpaEmployeeRepository jpaEmployeeRepository;

    public AuthServiceImpl(JpaUserRepository userRepository, PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager, JwtUtils jwtUtils, JpaEmployeeRepository jpaEmployeeRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.jpaEmployeeRepository = jpaEmployeeRepository;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void registerUser(SignupRequest signUpRequest) {
        if (existsByEmail(signUpRequest.getEmail())) {
            throw new ValidationException("El correo electrónico ya está en uso.");
        }

        UserApp user = new UserApp();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        // Convertir roles de String a la entidad Role
        Set<Role> roles = signUpRequest.getRoles().stream()
                .map(roleName -> new Role(null, ERole.valueOf(roleName)))
                .collect(Collectors.toSet());

        user.setRoles(roles);

        userRepository.save(user);
        log.info("Usuario registrado exitosamente con el correo: {}", signUpRequest.getEmail());
    }

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        try {
            // Autenticar al usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            // Establecer la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generar el token JWT
            String jwt = jwtUtils.generateJwtToken(authentication);

            // Obtener los detalles del usuario autenticado
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            // Convertir roles a una lista de cadenas (List<String>)
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            // Buscar el empleado relacionado si existe
            Long employeeId = null;
            Optional<Employee> employeeOptional = jpaEmployeeRepository.findByUserAppId(userDetails.getId());
            if (employeeOptional.isPresent()) {
                employeeId = employeeOptional.get().getId();
            }

            return new JwtResponse(
                    jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    roles,
                    employeeId);
        } catch (Exception e) {
            throw new ValidationException("Credenciales inválidas. Por favor, verifique su correo y contraseña.");
        }
    }
}