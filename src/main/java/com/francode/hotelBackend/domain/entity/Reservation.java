package com.francode.hotelBackend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Reservas")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "habitacion_id", nullable = false)
    private Room room;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "tipo_tarifa", nullable = false)
    private String rateType;

    @Column(name = "precio_tarifa", nullable = false)
    private BigDecimal priceRate;

    @Column(name = "precio_adicional", nullable = true)
    private BigDecimal additionalPrice;

    @Column(name = "precio_total", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "comentarios", nullable = true, length = 255)
    private String comments;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(name = "fecha_check_in")
    private LocalDateTime checkInDate;

    @Column(name = "fecha_check_out")
    private LocalDateTime checkOutDate;

    @Column(name = "fecha_cancelacion")
    private LocalDateTime cancellationDate;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;
}

