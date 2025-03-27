package com.francode.hotelBackend.presentation.dto.auth;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    private Long id;
    private String email;
    private List<String> roles;
    private Long employeeId;
}
