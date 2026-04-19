package com.example.springboot_realtimechat.dto;

import com.example.springboot_realtimechat.domain.Message;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
    Long messageId;
    String content;
    Long memberId;
    Long chatroomId;

    public MessageResponse(Long messageId, String content, Long memberId, Long chatroomId) {
        this.messageId = messageId;
        this.content = content;
        this.memberId = memberId;
        this.chatroomId = chatroomId;
    }

    public static MessageResponse from(Message message){
        return new MessageResponse(
                message.getId(),
                message.getContent(),
                message.getMember().getId(),
                message.getChatRoom().getId()
        );
    }
}
