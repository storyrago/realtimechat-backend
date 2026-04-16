package com.example.springboot_realtimechat.controller;

import com.example.springboot_realtimechat.domain.Member;
import com.example.springboot_realtimechat.dto.MemberRequest;
import com.example.springboot_realtimechat.dto.MemberResponse;
import com.example.springboot_realtimechat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberSerivce;

    @GetMapping
    public List<MemberResponse> getAllMembers(){
        List<Member> memberList = memberSerivce.getMemberList();
        return memberList.stream()
                .map(MemberResponse::from)
                .toList();
    }
    @GetMapping("/{id}")
    public MemberResponse getMemberById(@PathVariable Long id){
        Member member = memberSerivce.getMemberById(id);
        return MemberResponse.from(member);
    }

    @PostMapping
    public MemberResponse create(@RequestBody MemberRequest memberRequest){
       Member member = memberSerivce.create(
               memberRequest.getEmail(),
               memberRequest.getPassword(),
               memberRequest.getNickname()
       );

        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getCreatedAt());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id){
        memberSerivce.delete(id);
        return ResponseEntity.noContent().build();
    }
}