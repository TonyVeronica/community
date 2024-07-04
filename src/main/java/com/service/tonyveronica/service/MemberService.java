package com.service.tonyveronica.service;

import com.service.tonyveronica.domain.Member;

import java.util.List;

public interface MemberService {


    Long join(Member member);

    List<Member> findMembers();

    Member isDuplicateEmail(String email);

    Member isDuplicateNickname(String nickName);

}
