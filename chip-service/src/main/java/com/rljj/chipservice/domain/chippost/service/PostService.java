package com.rljj.chipservice.domain.chippost.service;

import com.rljj.chipservice.domain.chippost.dto.PostResponse;
import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import com.rljj.chipservice.domain.chippost.dto.PostRequest;
import org.springframework.data.domain.Page;

public interface PostService {

    Page<PostResponse> getPosts(int page, int size);

    PostResponse getPost(Long id);

    PostResponse createPost(PostRequest request);

    PostResponse updatePost(Long id, PostRequest request);

    void deletePost(Long id);
}