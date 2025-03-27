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
@Table(name = "Productos_Habitacion")
public class ProductRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 200)
    private String name;

    @Column(name = "descripcion", length = 200)
    private String description;

    @Column(name = "precio", nullable = false)
    private Double price;

    @Column(name = "cantidad", nullable = false)
    private Integer quantity;
}
