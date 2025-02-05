package com.rljj.chipservice.domain.chippost.controller;

import com.rljj.chipservice.domain.chippost.dto.ChipPostRequest;
import com.rljj.chipservice.domain.chippost.dto.ChipPostResponse;
import com.rljj.chipservice.domain.chippost.service.ChipPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/chip")
@RequiredArgsConstructor
public class ChipPostController {

    private final ChipPostService postService;

    @GetMapping("/posts")
    public ResponseEntity<Page<ChipPostResponse>> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "30") int size) {
        return ResponseEntity.ok(postService.getPosts(page, size));
    }

    @GetMapping("/post/{chipPostId}")
    public ResponseEntity<ChipPostResponse> getPost(@PathVariable Long chipPostId) {
        return ResponseEntity.ok(postService.getPost(chipPostId));
    }

    @PostMapping("/post")
    public ResponseEntity<ChipPostResponse> createPost(@RequestBody ChipPostRequest request) {
        return ResponseEntity.ok(postService.createPost(request));
    }

    @PatchMapping("/post/{chipPostId}")
    public ResponseEntity<ChipPostResponse> updatePost(
            @PathVariable Long chipPostId, @RequestBody ChipPostRequest request) {
        return ResponseEntity.ok(postService.updatePost(chipPostId, request));
    }

    @DeleteMapping("/post/{chipPostId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long chipPostId) {
        postService.deletePost(chipPostId);
        return ResponseEntity.noContent().build();
    }
}
