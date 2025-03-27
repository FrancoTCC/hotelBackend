package com.francode.hotelBackend.exceptions.advice;

import com.francode.hotelBackend.exceptions.DTOs.ApiErrorDTO;
import com.francode.hotelBackend.exceptions.DTOs.ApiResponseDTO;
import com.francode.hotelBackend.exceptions.custom.NoRecordsException;
import com.francode.hotelBackend.exceptions.custom.NotFoundException;
import com.francode.hotelBackend.exceptions.custom.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de excepción 404 - URL no existe
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponseDTO> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        ApiResponseDTO response = new ApiResponseDTO(404, "La URL solicitada no existe: " + ex.getRequestURL());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Manejo de excepción 404 - Recurso no encontrado
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponseDTO> hadleNotFoundException(NotFoundException ex) {
        ApiResponseDTO response = new ApiResponseDTO(404, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Manejo de excepción 400 - Error de validación
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponseDTO> handleValidationException(ValidationException ex) {
        ApiResponseDTO response = new ApiResponseDTO(400, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Manejo de excepción 200 - Excepción sin registros
    @ExceptionHandler(NoRecordsException.class)
    public ResponseEntity<ApiResponseDTO> handleNoRecordsException(NoRecordsException ex) {
        ApiResponseDTO response = new ApiResponseDTO(200, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Manejar excepciones de validación
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult().getAllErrors().stream()
                .map(error -> ((FieldError) error).getDefaultMessage())
                .collect(Collectors.toList());

        String errorMessage = String.join(", ", errorMessages);
        ApiResponseDTO errorResponse = new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(), errorMessage);


        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Manejo de excepción 403 - Sin permisos
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "403");
        response.put("message", "No tiene permisos para acceder a este recurso.");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleGlobalException(Exception ex, WebRequest request) {
        ApiErrorDTO errorDTO = new ApiErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocurrió un error inesperado",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}