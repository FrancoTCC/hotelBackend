package com.francode.hotelBackend.business.services.impl;

import com.francode.hotelBackend.business.services.interfaces.PaymentService;
import com.francode.hotelBackend.business.mapper.PaymentMapper;
import com.francode.hotelBackend.domain.entity.Payment;
import com.francode.hotelBackend.domain.entity.Reservation;
import com.francode.hotelBackend.exceptions.custom.NoRecordsException;
import com.francode.hotelBackend.exceptions.custom.ValidationException;
import com.francode.hotelBackend.exceptions.custom.NotFoundException;
import com.francode.hotelBackend.persistence.repository.JpaPaymentRepository;
import com.francode.hotelBackend.persistence.repository.JpaReservationRepository;
import com.francode.hotelBackend.presentation.dto.request.PaymentRequestDTO;
import com.francode.hotelBackend.presentation.dto.response.PaymentResponseDTO;
import jakarta.persistence.criteria.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final JpaPaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final JpaReservationRepository reservationRepository;

    @Autowired
    public PaymentServiceImpl(JpaPaymentRepository paymentRepository,
                              PaymentMapper paymentMapper,
                              JpaReservationRepository reservationRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Optional<PaymentResponseDTO> findById(Long id) {
        if (id == null) {
            throw new ValidationException("El ID del pago no puede ser nulo.");
        }
        return paymentRepository.findById(id)
                .map(paymentMapper::toResponseDTO)
                .or(() -> {
                    throw new NotFoundException("No se encontró un pago con el ID: " + id);
                });
    }

    @Override
    public PaymentResponseDTO create(PaymentRequestDTO paymentRequestDTO) {
        if (paymentRequestDTO == null) {
            throw new ValidationException("La solicitud de creación de pago no puede ser nula.");
        }

        Reservation reservation = reservationRepository.findById(paymentRequestDTO.getReservationId())
                .orElseThrow(() -> new NotFoundException("No se encontró una reserva con el ID: " + paymentRequestDTO.getReservationId()));

        Payment payment = paymentMapper.toEntity(paymentRequestDTO);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setReservation(reservation);
        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.toResponseDTO(savedPayment);
    }

    @Override
    public PaymentResponseDTO update(Long id, PaymentRequestDTO paymentRequestDTO) {
        if (id == null) {
            throw new ValidationException("El ID del pago no puede ser nulo.");
        }

        if (paymentRequestDTO == null) {
            throw new ValidationException("La solicitud de actualización de pago no puede ser nula.");
        }

        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró un pago con el ID: " + id));

        Reservation reservation = reservationRepository.findById(paymentRequestDTO.getReservationId())
                .orElseThrow(() -> new NotFoundException("No se encontró una reserva con el ID: " + paymentRequestDTO.getReservationId()));

        paymentMapper.updateEntityFromDTO(paymentRequestDTO, existingPayment);
        existingPayment.setReservation(reservation);
        Payment updatedPayment = paymentRepository.save(existingPayment);

        return paymentMapper.toResponseDTO(updatedPayment);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new ValidationException("El ID del pago no puede ser nulo.");
        }

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró un pago con el ID: " + id));

        paymentRepository.delete(payment);
    }

    public Page<PaymentResponseDTO> findAll(String field, String value, Pageable pageable) {
        if ((field != null && value == null) || (field == null && value != null)) {
            throw new ValidationException("Ambos, campo y valor, deben proporcionarse para la búsqueda.");
        }

        Specification<Payment> spec = Specification.where(null);

        if (field != null && value != null && !field.isEmpty() && !value.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Path<String> fieldPath = root.get(field);
                return criteriaBuilder.like(criteriaBuilder.lower(fieldPath), "%" + value.toLowerCase() + "%");
            });
        }

        Page<Payment> payments = paymentRepository.findAll(spec, pageable);

        if (payments.isEmpty()) {
            throw new NoRecordsException("Todavía no hay registros disponibles.");
        }

        return payments.map(paymentMapper::toResponseDTO);
    }
}