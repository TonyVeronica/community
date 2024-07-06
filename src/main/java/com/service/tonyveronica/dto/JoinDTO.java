package com.service.tonyveronica.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class JoinDTO {
    private String email;
    private String password;
    private String nickName;
    private String imagePath;

    public JoinDTO(String email, String password, String nickName, String imagePath) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.imagePath = imagePath;
    }
}
