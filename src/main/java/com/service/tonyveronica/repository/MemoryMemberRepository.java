package com.service.tonyveronica.repository;

import com.service.tonyveronica.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryMemberRepository implements MemberRepository{
    @Override
    public int save(Member member) {
        return 0;
    }
}
