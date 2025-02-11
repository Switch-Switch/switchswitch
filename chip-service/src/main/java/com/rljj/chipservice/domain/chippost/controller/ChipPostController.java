package com.rljj.chipservice.domain.chippost.controller;

import com.rljj.chipservice.domain.chippost.dto.ChipPostRequest;
import com.rljj.chipservice.domain.chippost.dto.ChipPostResponse;
import com.rljj.chipservice.domain.chippost.service.ChipPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ChipPostResponse> createPost(@AuthenticationPrincipal UserDetails userDetails,
                                                       @RequestBody ChipPostRequest request) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(postService.createPost(memberId, request));
    }

    @PatchMapping("/post/{chipPostId}")
    public ResponseEntity<ChipPostResponse> updatePost(@AuthenticationPrincipal UserDetails userDetails,
                                                       @PathVariable Long chipPostId,
                                                       @RequestBody ChipPostRequest request) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(postService.updatePost(memberId, chipPostId, request));
    }

    @DeleteMapping("/post/{chipPostId}")
    public ResponseEntity<Void> deletePost(@AuthenticationPrincipal UserDetails userDetails,
                                           @PathVariable Long chipPostId) {
        Long memberId = Long.parseLong(userDetails.getUsername());
        postService.deletePost(memberId, chipPostId);
        return ResponseEntity.ok().build();
    }
}
