package com.francode.hotelBackend.business.services.impl;

import com.francode.hotelBackend.business.services.interfaces.WebSocketService;
import com.francode.hotelBackend.domain.entity.Room;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebSocketServiceImpl implements WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void sendRoomUpdate(Room room) {
        messagingTemplate.convertAndSend("/topic/rooms", room);
    }

    @Override
    public void sendRoomDeletionNotification(Room room) {
        messagingTemplate.convertAndSend("/topic/rooms", "Room deleted: " + room.getId());
    }

    @Override
    public void sendRoomStatusUpdate(Long roomId, String statusCleaning) {
        // Crear un Map para enviar solo los datos necesarios
        Map<String, Object> roomStatusUpdate = new HashMap<>();
        roomStatusUpdate.put("roomId", roomId);
        roomStatusUpdate.put("statusCleaning", statusCleaning); // Solo el campo que deseas

        // Enviar solo el cambio a través de WebSocket
        messagingTemplate.convertAndSend("/topic/rooms", roomStatusUpdate);
    }
}

