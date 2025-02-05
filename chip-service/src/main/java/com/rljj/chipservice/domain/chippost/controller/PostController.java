package com.rljj.chipservice.domain.chippost.controller;

import com.rljj.chipservice.domain.chippost.dto.PostRequest;
import com.rljj.chipservice.domain.chippost.dto.PostResponse;
import com.rljj.chipservice.domain.chippost.service.PostServiceImpl;
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

    @GetMapping("/posts")
    public ResponseEntity<Page<PostResponse>> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "30") int size) {
        return ResponseEntity.ok(postService.getPosts(page, size));
    }

    @GetMapping("/post/{chipPostId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long chipPostId) {
        return ResponseEntity.ok(postService.getPost(chipPostId));
    }

    @PostMapping("/post")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest request) {
        return ResponseEntity.ok(postService.createPost(request));
    }

    @PutMapping("/post/{chipPostId}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long chipPostId, @RequestBody PostRequest request) {
        return ResponseEntity.ok(postService.updatePost(chipPostId, request));
    }

    @DeleteMapping("/post/{chipPostId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long chipPostId) {
        postService.deletePost(chipPostId);
        return ResponseEntity.noContent().build();
    }
}
