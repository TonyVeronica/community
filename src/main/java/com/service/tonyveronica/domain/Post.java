package com.service.tonyveronica.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="posts")
@Data
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long postId;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="content", nullable = false)
    private String content;

    @Column(name="post_image")
    private String postImagePath;

    @Column(name="is_deleted")
    private boolean isDeleted;

    @Column(name="views")
    private Long views;

    @Column(name="member_email")
    private String memberEmail;
}
