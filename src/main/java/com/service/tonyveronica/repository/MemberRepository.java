package com.service.tonyveronica.repository;

import com.service.tonyveronica.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m  from Member m where m.email = :email")
    Member existsByEmail(@Param("email") String email);

    @Query("select m from Member m where m.nickName = :nickname")
    Member existsByNickname(@Param("nickname") String nickname);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.nickName = :nickname where m.email = :email")
    int updateMemberByNickName(@Param("nickname")String nickname, @Param("email")String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member m SET m.password = :password where m.email = :email")
    int updateMemberByPassword(@Param("password")String password, @Param("email")String email);

    @Modifying(clearAutomatically = true)
    @Query("delete from Member m where m.email = :email")
    int deleteByEmail(@Param("email")String email);
}
