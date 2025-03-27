package com.francode.hotelBackend.domain.entity;

public enum EStatusRoom {
    OCUPADO("Ocupado"),
    DISPONIBLE("Disponible"),
    MANTENIMIENTO("Mantenimiento");

    private final String descripcion;

    EStatusRoom(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
