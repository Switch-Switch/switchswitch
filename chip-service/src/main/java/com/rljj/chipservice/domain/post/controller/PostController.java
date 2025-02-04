package com.rljj.chipservice.domain.post.controller;

import com.rljj.chipservice.domain.post.dto.PostRequest;
import com.rljj.chipservice.domain.post.service.PostServiceImpl;
import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chip")
public class PostController {

    private final PostServiceImpl postService;

    // 전체 게시물 조회
    @GetMapping("/posts")
    public ResponseEntity<Page<ChipPost>> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "30") int size) {

        Page<ChipPost> postPage = postService.getPosts(page, size);
        return ResponseEntity.ok(postPage);
    }

    // 게시물 생성
    @PostMapping("/post")
    public ChipPost createPost(@RequestBody PostRequest request) {
        return postService.createPost(request);
    }

    // 게시물 상세 조회
    @GetMapping("/post/{chipPostId}")
    public ChipPost getPost(@PathVariable Long chipPostId) {
        return postService.getPost(chipPostId);
    }

    // 게시물 수정
    @PutMapping("/post/{chipPostId}")
    public ChipPost updatePost(@PathVariable Long chipPostId, @RequestBody PostRequest request) {
        return postService.updatePost(chipPostId, request);
    }

    // 게시물 삭제
    @DeleteMapping("/post/{chipPostId}")
    public void deletePost(@PathVariable Long chipPostId) {
        postService.deletePost(chipPostId);
    }
}