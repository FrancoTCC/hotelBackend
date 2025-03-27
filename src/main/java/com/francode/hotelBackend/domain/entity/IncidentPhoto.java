package com.francode.hotelBackend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "incidencia_fotos")
public class IncidentPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "incidencia_id", nullable = false)
    private Incident incident;

    @Column(name = "url_imagen", nullable = false)
    private String imageUrl;
}

