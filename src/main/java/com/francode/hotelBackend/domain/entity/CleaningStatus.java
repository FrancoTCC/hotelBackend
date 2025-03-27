package com.francode.hotelBackend.domain.entity;

public enum CleaningStatus {
    EN_PROCESO("En Proceso"),
    TERMINADO("Terminado"),
    CANCELADO("Cancelado");

    private final String descripcion;

    CleaningStatus(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
