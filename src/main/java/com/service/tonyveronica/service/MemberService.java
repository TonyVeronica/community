package com.service.tonyveronica.service;

import com.service.tonyveronica.domain.Member;
import com.service.tonyveronica.dto.JoinDTO;

import java.util.List;

public interface MemberService {


    Long join(JoinDTO joinDTO);

    List<Member> findMembers();

    Member isDuplicateEmail(String email);

    Member isDuplicateNickname(String nickName);

    int updateNickname(String nickname, String email);

    int updatePassword(String password, String email);

}
