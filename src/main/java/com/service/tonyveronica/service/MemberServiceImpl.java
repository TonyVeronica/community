package com.service.tonyveronica.service;

import com.service.tonyveronica.domain.Member;
import com.service.tonyveronica.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberRepository memberRepository;
    @Override
    public int joinMember(Member member) {
        return 0;
    }
}
