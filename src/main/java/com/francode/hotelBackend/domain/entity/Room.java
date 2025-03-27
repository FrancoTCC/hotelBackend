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
@Table(name = "Habitaciones")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_habitacion_id", nullable = false)
    private RoomType roomType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "piso_id", nullable = false)
    private FloorRooms floor;

    @Column(name = "numero", nullable = false, length = 10)
    private String number;

    @Column(name = "descripcion", nullable = false)
    private String description;

    @Column(name = "precio_diario", nullable = false)
    private Double dailyPrice;

    @Column(name = "precio_horario", nullable = false)
    private Double hourlyPrice;

    @Column(name = "estado", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EStatusRoom status;

    @Column(name = "estado_limpieza")
    @Enumerated(EnumType.STRING)
    private EStatusCleaningRoom statusCleaning;
}

