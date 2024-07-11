package com.service.tonyveronica.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="comments")
@Data
@NoArgsConstructor //기본 생성자
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long commentId;

    @Column(name="comment", nullable = false)
    private String comment;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="is_deleted")
    private Boolean isDeleted;

    @Column(name="post_id")
    private Long postId;

    @Column(name="member_email")
    private Long memberEmail;

//    @ManyToOne(optional = false)
//    @JoinTable(name = "posts",
//            joinColumns = @JoinColumn(name = "comment_id"),
//            inverseJoinColumns = @JoinColumn(name = "post_id")
//    )
//    private Post post;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isDeleted == null) {
            isDeleted = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
