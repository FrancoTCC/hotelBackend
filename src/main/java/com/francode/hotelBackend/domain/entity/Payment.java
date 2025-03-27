package com.francode.hotelBackend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Pagos")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reservation reservation;

    @Column(name = "monto", nullable = false)
    private BigDecimal amount;

    @Column(name = "metodo_pago", nullable = false, length = 20)
    private String paymentMethod;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime paymentDate;
}


