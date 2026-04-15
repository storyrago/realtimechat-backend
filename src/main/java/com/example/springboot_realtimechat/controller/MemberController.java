package com.example.springboot_realtimechat.controller;

import com.example.springboot_realtimechat.domain.Member;
import com.example.springboot_realtimechat.dto.MemberRequest;
import com.example.springboot_realtimechat.dto.MemberResponse;
import com.example.springboot_realtimechat.service.MemberSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberSerivce memberSerivce;

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
}