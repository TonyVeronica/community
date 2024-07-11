package com.service.tonyveronica.repository;

import com.service.tonyveronica.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.postId = :postId")
    Long countCommentsByPostId(@Param("postId") Long postId);
}
