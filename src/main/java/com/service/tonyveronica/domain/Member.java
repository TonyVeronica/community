package com.service.tonyveronica.domain;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name="users")
@Setter
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //기본키 자동 생성
    private Long id;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="nickname", nullable = false)
    private String nickName;

    @Column(name="user_image", nullable = false)
    private String imagePath;

    @Column(name="delete", nullable = false)
    private boolean isDelete; //삭제 여부

    public Member() {
    }

    public Member(String email, String password, String nickName, boolean isDelete) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "Member{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", isDelete=" + isDelete +
                '}';
    }
}
