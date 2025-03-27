package com.francode.hotelBackend.domain.entity;


public enum EStatusCleaningRoom {
    LIMPIO("Limpio"),
    PARA_LIMPIAR("Para Limpiar"),
    LIMPIANDO("Limpiando");

    private final String descripcion;

    EStatusCleaningRoom(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}