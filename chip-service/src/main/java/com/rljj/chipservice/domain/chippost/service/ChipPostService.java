package com.rljj.chipservice.domain.chippost.service;

import com.rljj.chipservice.domain.chippost.dto.ChipPostRequest;
import com.rljj.chipservice.domain.chippost.dto.ChipPostResponse;
import org.springframework.data.domain.Page;

public interface ChipPostService {

    Page<ChipPostResponse> getPosts(int page, int size);

    ChipPostResponse getPost(Long chipPostId);

    ChipPostResponse createPost(Long memberId, ChipPostRequest request);

    ChipPostResponse updatePost(Long memberId, Long chipPostId, ChipPostRequest request);

    void deletePost(Long memberId, Long chipPostId);
}