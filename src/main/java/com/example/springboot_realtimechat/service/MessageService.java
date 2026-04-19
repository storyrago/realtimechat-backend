package com.example.springboot_realtimechat.service;

import com.example.springboot_realtimechat.domain.ChatRoom;
import com.example.springboot_realtimechat.domain.Member;
import com.example.springboot_realtimechat.domain.Message;
import com.example.springboot_realtimechat.repository.ChatRoomMemberRepository;
import com.example.springboot_realtimechat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MemberService memberService;
    private final ChatRoomService chatRoomService;

    @Transactional
    public Message create(String content, Long memberId, Long chatroomId){
        Member member = memberService.getMemberById(memberId);
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatroomId);

        boolean exists = chatRoomMemberRepository.existsByMemberAndChatRoom(member, chatRoom);
        if(!exists){
            throw new RuntimeException("채팅방에 참여하지 않은 유저임.");
        }

        Message message = new Message(content, member, chatRoom);
        return messageRepository.save(message);
    }

    public Message getMessageById(Long messageId){
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("메시지 없음."));
    }

    public List<Message> getAllChatRoomMessages(Long chatroomId){
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatroomId);
        return messageRepository.findByChatRoomOrderById(chatRoom);
    }
}
