package com.service.tonyveronica.service;

import com.service.tonyveronica.domain.Post;
import com.service.tonyveronica.dto.PostCreateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Long createPost(PostCreateDTO postCreateDTO, String email);

    List<Post> getAllPosts();

    Post getOnePost(Long postId);

    Long countComments(Long postId);

    Page<Post> getAllPosts(Pageable pageable);
}
