package com.service.tonyveronica.service;

import com.service.tonyveronica.domain.Member;
import com.service.tonyveronica.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor //밑에 memberRepository의 생성자를 쓰지 않기 위해서
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    @Override
    public Long join(Member member) {
        return memberRepository.save(member).getMember_id();
    }

    @Override
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member isDuplicateEmail(String email){
        return memberRepository.existsByEmail(email);
    }

    @Override
    public Member isDuplicateNickname(String nickName) {
        return memberRepository.existsByNickname(nickName);
    }


}
