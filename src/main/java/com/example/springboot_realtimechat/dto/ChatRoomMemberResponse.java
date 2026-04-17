package com.example.springboot_realtimechat.dto;

import com.example.springboot_realtimechat.domain.ChatRoom;
import com.example.springboot_realtimechat.domain.ChatRoomMember;
import com.example.springboot_realtimechat.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomMemberResponse {
    private Long id;
    private Long memberId;
    private Long chatRoomId;

    public ChatRoomMemberResponse(Long id, Long memberId, Long chatRoomId) {
        this.id = id;
        this.memberId = memberId;
        this.chatRoomId = chatRoomId;
    }

    public static ChatRoomMemberResponse from(ChatRoomMember chatRoomMember){
        return new ChatRoomMemberResponse(
                chatRoomMember.getId(),
                chatRoomMember.getMember().getId(),
                chatRoomMember.getChatRoom().getId()
        );
    }
}
