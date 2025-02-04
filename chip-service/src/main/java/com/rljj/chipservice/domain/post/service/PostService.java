package com.rljj.chipservice.domain.post.service;

import com.rljj.chipservice.domain.post.dto.PostDataResponse;
import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import com.rljj.chipservice.domain.post.dto.PostRequest;
import org.springframework.data.domain.Page;

public interface PostService {

    PostDataResponse<Page<ChipPost>> getPosts(int page, int size);

    PostDataResponse<ChipPost> getPost(Long id);

    PostDataResponse<ChipPost> createPost(PostRequest request);

    PostDataResponse<ChipPost> updatePost(Long id, PostRequest request);

    PostDataResponse<Void> deletePost(Long id);
}