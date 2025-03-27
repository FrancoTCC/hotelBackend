package com.francode.hotelBackend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "limpiezas")
public class Cleaning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "habitacion_id", nullable = false)
    private Room room;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "fecha_fin")
    private LocalDateTime endDate;

    @Column(name = "fecha_cancelacion")
    private LocalDateTime cancellationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private CleaningStatus status;

    @OneToMany(mappedBy = "cleaning", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Incident> incidents;
}
