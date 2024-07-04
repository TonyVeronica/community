package com.service.tonyveronica.domain;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name="members")
@Setter
@Getter
@NoArgsConstructor //기본 생성자
public class Member {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //기본키 자동 생성
    private Long member_id;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="nickname", nullable = false)
    private String nickName;

    @Column(name="user_image", nullable = false)
    private String imagePath;

    @Column(name="is_deleted", nullable = false)
    private boolean isDelete; //삭제 여부


    public Member(String email, String password, String nickName, String imagePath, boolean isDelete) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.imagePath = imagePath;
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
