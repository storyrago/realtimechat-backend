package com.example.springboot_realtimechat.dto;

import com.example.springboot_realtimechat.domain.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberResponse {
    Long id;
    String email;
    String nickname;
    LocalDateTime createAt;

    public MemberResponse(Long id, String email, String nickname, LocalDateTime createAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.createAt = createAt;
    }

    public static MemberResponse from(Member member){
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getCreatedAt()
        );
    }
}
