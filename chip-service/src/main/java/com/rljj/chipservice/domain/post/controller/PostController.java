package com.rljj.chipservice.domain.post.controller;

import com.rljj.chipservice.domain.post.dto.PostRequest;
import com.rljj.chipservice.domain.post.service.PostServiceImpl;
import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chip")
public class PostController {

    private final PostServiceImpl postService;

    // 전체 게시물 조회
    @GetMapping("/posts")
    public List<ChipPost> getPosts(@RequestParam int page, @RequestParam int size) {
        return postService.getPosts(page, size);
    }

    // 게시물 생성
    @PostMapping("/post")
    public ChipPost createPost(@RequestBody PostRequest request) {
        return postService.createPost(request);
    }

    // 게시물 상세 조회
    @GetMapping("/post/{chipPostId}")
    public ChipPost getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    // 게시물 수정
    @PutMapping("/post/{chipPostId}")
    public ChipPost updatePost(@PathVariable Long postId, @RequestBody PostRequest request) {
        return postService.updatePost(postId, request);
    }

    // 게시물 삭제
    @DeleteMapping("/post/{chipPostId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}