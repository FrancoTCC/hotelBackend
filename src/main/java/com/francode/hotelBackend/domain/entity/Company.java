package com.francode.hotelBackend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Empresas")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String name;

    @Column(name = "correo", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "ruc", nullable = false, unique = true, length = 11)
    private String ruc;

    @Column(name = "ubicacion", nullable = false, length = 350)
    private String location;

    @Column(name = "telefono", nullable = false, length = 20)
    private String phone;

    @Column(name = "logo_url", nullable = false, length = 255)
    private String logoUrl;
}
