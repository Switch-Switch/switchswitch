package com.rljj.chipservice.domain.post.service;

import com.rljj.chipservice.domain.post.dto.PostRequest;
import com.rljj.chipservice.domain.post.repository.PostRepository;
import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import com.rljj.switchswitchentity.chip.chipinfo.ChipInfo;
import com.rljj.switchswitchentity.member.Member;
import com.rljj.switchswitchentity.chip.chippost.ChipPostStatus;
//import com.rljj.chipservice.domain.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import java.util.List;

// null 체크, setter 쓰지 않기 등  
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public List<ChipPost> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdDate").descending());
        return postRepository.findAll(pageable).getContent();
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

        return postRepository.save(existingPost);
    }

    @Override
    public ChipPost getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id)); //밑줄 왜 생겨?
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}