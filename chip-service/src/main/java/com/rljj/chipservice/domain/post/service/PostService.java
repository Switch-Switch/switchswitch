package com.rljj.chipservice.domain.post.service;

import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import com.rljj.chipservice.domain.post.dto.PostRequest;
import java.util.List;

public interface PostService {

    List<ChipPost> getPosts(int page, int size);

    ChipPost createPost(PostRequest request);

    ChipPost getPostById(Long id);

    ChipPost updatePost(Long id, PostRequest request);

    void deletePost(Long id);
}