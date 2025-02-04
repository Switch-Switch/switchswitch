package com.rljj.chipservice.domain.post.service;

import com.rljj.chipservice.domain.post.dto.PostRequest;
import com.rljj.chipservice.domain.post.repository.PostRepository;
import com.rljj.switchswitchcommon.exception.NotFoundException;
import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import com.rljj.switchswitchentity.chip.chipinfo.ChipInfo;
import com.rljj.switchswitchentity.member.Member;
import com.rljj.switchswitchentity.chip.chippost.ChipPostStatus;
//import com.rljj.chipservice.domain.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public Page<ChipPost> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdDate").descending());
        return postRepository.findAll(pageable);
    }

    @Override
    public ChipPost getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
    }

    @Override
    @Transactional
    public ChipPost createPost(PostRequest request) {

        Member member = Member.builder()
            .id(request.getMemberId())
            .build();
        ChipInfo chipInfo = ChipInfo.builder()
            .id(request.getChipInfoId())
            .build();

        ChipPost newPost = ChipPost.builder()
                .chipInfo(chipInfo)
                .member(member)
                .title(request.getTitle())
                .description(request.getDescription())
                .status(ChipPostStatus.valueOf(request.getStatus()))
                .build();

        return postRepository.save(newPost);
    }

    @Override
    @Transactional
    public ChipPost updatePost(Long chipPostId, PostRequest request) {
        ChipPost existingPost = postRepository.findById(chipPostId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID:" + chipPostId));

        existingPost.update(
                request.getTitle(),
                request.getDescription(),
                request.getStatus()
        );

        return existingPost;
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new NotFoundException(String.valueOf(id));
        }
        postRepository.deleteById(id);
    }
}