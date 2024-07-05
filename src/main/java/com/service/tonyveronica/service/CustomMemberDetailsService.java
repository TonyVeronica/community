package com.service.tonyveronica.service;

import com.service.tonyveronica.domain.Member;
import com.service.tonyveronica.dto.CustomMemberDetails;
import com.service.tonyveronica.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //밑에 memberRepository의 생성자를 쓰지 않기 위해서
public class CustomMemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.existsByEmail(email);

        if(member != null){
            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomMemberDetails(member);
        }
        return null;
    }
}
