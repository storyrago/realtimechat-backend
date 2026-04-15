package com.example.springboot_realtimechat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequest {
    String email;
    String password;
    String nickname;
}
