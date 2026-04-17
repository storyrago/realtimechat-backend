package com.example.springboot_realtimechat.service;

import com.example.springboot_realtimechat.domain.ChatRoom;
import com.example.springboot_realtimechat.domain.ChatRoomMember;
import com.example.springboot_realtimechat.domain.Member;
import com.example.springboot_realtimechat.repository.ChatRoomMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomMemberService {
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MemberService memberService;
    private final ChatRoomService chatRoomService;

    @Transactional
    public ChatRoomMember join(Long memberId, Long chatRoomId){
        Member member = memberService.getMemberById(memberId);
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatRoomId);

        //이미 존재하는 지 확인하는거. (중복 방지)
        boolean exists = chatRoomMemberRepository
                .existsByMemberAndChatRoom(member, chatRoom);

        if(exists){
            throw new RuntimeException("이미 참여한 멤버임.");
        }

        ChatRoomMember chatRoomMember = new ChatRoomMember(member, chatRoom);
        return chatRoomMemberRepository.save(chatRoomMember);
    }

    @Transactional
    public void leave(Long memberId, Long chatRoomId){
        Member member = memberService.getMemberById(memberId);
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatRoomId);
        ChatRoomMember chatRoomMember = chatRoomMemberRepository
                .findByMemberAndChatRoom(member, chatRoom)
                        .orElseThrow(()->new RuntimeException("참여 정보 없음."));

        chatRoomMemberRepository.delete(chatRoomMember);
    }

    public List<ChatRoomMember> getChatRoomMembersById(Long chatRoomId){
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatRoomId);

        return chatRoomMemberRepository.findByChatRoom(chatRoom);
    }
}
