package com.service.tonyveronica.repository;

import com.service.tonyveronica.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m  from Member m where m.email = :email")
    Member existsByEmail(@Param("email") String email);

    @Query("select m from Member m where m.nickName = :nickname")
    Member existsByNickname(@Param("nickname") String nickname);
}
