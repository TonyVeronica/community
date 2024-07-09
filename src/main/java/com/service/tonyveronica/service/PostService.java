package com.service.tonyveronica.service;

import com.service.tonyveronica.domain.Post;
import com.service.tonyveronica.dto.PostCreateDTO;

import java.util.List;

public interface PostService {
    Long createPost(PostCreateDTO postCreateDTO, String email);

    List<Post> getAllPosts();
}
