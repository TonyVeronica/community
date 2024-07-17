package com.service.tonyveronica.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class responseDTO {
    private Long postId;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long views;
    private Long likes;

    private Long comments;
    private String email;
    private String userImage;
    private String nickname;

    public responseDTO(Long postId, String title, LocalDateTime createdAt, LocalDateTime updatedAt, Long views, Long likes, Long comments, String email, String userImage, String nickname) {
        this.postId = postId;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.views = views;
        this.likes = likes;
        this.comments = comments;
        this.email = email;
        this.userImage = userImage;
        this.nickname = nickname;
    }
}
