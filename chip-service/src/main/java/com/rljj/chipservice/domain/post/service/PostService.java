package com.rljj.chipservice.domain.post.service;

import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import com.rljj.chipservice.domain.post.dto.PostRequest;
import org.springframework.data.domain.Page;

public interface PostService {

    Page<ChipPost> getPosts(int page, int size);

    ChipPost createPost(PostRequest request);

    ChipPost getPost(Long id);

    ChipPost updatePost(Long id, PostRequest request);

    void deletePost(Long id);
}