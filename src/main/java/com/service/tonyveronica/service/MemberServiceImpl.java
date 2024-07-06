package com.service.tonyveronica.service;

import com.service.tonyveronica.domain.Member;
import com.service.tonyveronica.dto.JoinDTO;
import com.service.tonyveronica.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor //밑에 memberRepository의 생성자를 쓰지 않기 위해서
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Long join(JoinDTO joinDTO) {
        String email = joinDTO.getEmail();
        String password = joinDTO.getPassword();
        String nickName = joinDTO.getNickName();
        String imagePath = joinDTO.getImagePath();

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(bCryptPasswordEncoder.encode(password));
        member.setNickName(nickName);
        member.setImagePath(imagePath);
        member.setDelete(false);
        member.setRole("ROLE_ADMIN");

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
