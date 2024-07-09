package com.service.tonyveronica.dto;

import lombok.Data;

@Data
public class PostCreateDTO {
    private String title;
    private String content;
    private String postImagePath;

    public PostCreateDTO(String title, String content, String postImagePath) {
        this.title = title;
        this.content = content;
        this.postImagePath = postImagePath;
    }

}
