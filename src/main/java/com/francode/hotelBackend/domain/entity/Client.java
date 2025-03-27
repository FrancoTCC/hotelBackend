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
@Table(name = "Clientes")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_cliente", nullable = false, length = 20)
    private String clientType;

    @Column(name = "tipo_documento", nullable = false, length = 20)
    private String documentType;

    @Column(name = "numero_documento", nullable = false, unique = true, length = 20)
    private String documentNumber;

    @Column(name = "nombre", nullable = false, length = 100)
    private String name;

    @Column(name = "telefono", nullable = true, length = 255)
    private String phone;
}

