package com.rljj.chipservice.domain.post.controller;

import com.rljj.chipservice.domain.post.dto.PostDataResponse;
import com.rljj.chipservice.domain.post.dto.PostErrorResponse;
import com.rljj.chipservice.domain.post.dto.PostRequest;
import com.rljj.chipservice.domain.post.service.PostServiceImpl;
import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/chip")
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postService;

    // 전체 게시물 조회
    @GetMapping("/posts")
    public ResponseEntity<PostDataResponse<Page<ChipPost>>> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "30") int size) {
        return ResponseEntity.ok(postService.getPosts(page, size));
    }

    // 게시물 상세 조회
    @GetMapping("/post/{chipPostId}")
    public ResponseEntity<PostDataResponse<ChipPost>> getPost(@PathVariable Long chipPostId) {
        return ResponseEntity.ok(postService.getPost(chipPostId));
    }


    // 게시물 생성
    @PostMapping("/post")
    public ResponseEntity<PostDataResponse<ChipPost>> createPost(@RequestBody PostRequest request) {
        return ResponseEntity.ok(postService.createPost(request));
    }

    // 게시물 수정
    @PutMapping("/post/{chipPostId}")
    public ResponseEntity<PostDataResponse<ChipPost>> updatePost(
            @PathVariable Long chipPostId, @RequestBody PostRequest request) {
        return ResponseEntity.ok(postService.updatePost(chipPostId, request));
    }

    // 게시물 삭제
    @DeleteMapping("/post/{chipPostId}")
    public ResponseEntity<PostDataResponse<Void>> deletePost(@PathVariable Long chipPostId) {
        return ResponseEntity.ok(postService.deletePost(chipPostId));
    }
}
