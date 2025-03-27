package com.francode.hotelBackend.domain.entity;

public enum ReservationStatus {
    PENDIENTE("Pendiente"),
    CONFIRMADA("Confirmada"),
    EN_EL_CHECK_IN("Check-in"),
    EN_EL_CHECK_OUT("Check-out"),
    CANCELADA("Cancelada"),
    COMPLETADA("Completada"),
    NO_SE_PRESENTO("No se presentó");

    private final String displayName;

    // Constructor para asociar un nombre legible con el estado
    ReservationStatus(String displayName) {
        this.displayName = displayName;
    }

    // Método para obtener el nombre legible
    public String getDisplayName() {
        return displayName;
    }
}
