package com.example.springboot_realtimechat.repository;


import com.example.springboot_realtimechat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
