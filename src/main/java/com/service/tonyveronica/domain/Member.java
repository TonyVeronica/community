package com.service.tonyveronica.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Member {
    private String email;
    private String password;
    private String nickName;
    private boolean isDelete; //삭제 여부


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
