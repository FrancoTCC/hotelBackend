package com.francode.hotelBackend.business.services.interfaces;

import com.francode.hotelBackend.domain.entity.Room;

public interface WebSocketService {
    void sendRoomUpdate(Room room);
    void sendRoomDeletionNotification(Room room);
    void sendRoomStatusUpdate(Long roomId, String statusCleaning);
}