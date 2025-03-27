package com.francode.hotelBackend.exceptions.custom;

public class NoRecordsException extends RuntimeException {
    public NoRecordsException(String message) {
        super(message);
    }
}
