package com.example.springboot_realtimechat.dto;

import com.example.springboot_realtimechat.domain.ChatRoom;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatRoomResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;

    public ChatRoomResponse(Long id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static ChatRoomResponse from(ChatRoom chatRoom){
        return new ChatRoomResponse(
                chatRoom.getId(),
                chatRoom.getName(),
                chatRoom.getCreatedAt()
        );
    }
}
