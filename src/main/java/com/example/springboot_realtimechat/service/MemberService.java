package com.example.springboot_realtimechat.service;

import com.example.springboot_realtimechat.domain.Member;
import com.example.springboot_realtimechat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public List<Member> getMemberList(){
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id){
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("멤버 없음."));
    }

    @Transactional
    public Member create(String email, String password, String nickname){
        Member member = new Member(email, password, nickname);
        return memberRepository.save(member);
    }

    @Transactional
    public void delete(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("멤버 없음."));
        memberRepository.delete(member);
    }
}
