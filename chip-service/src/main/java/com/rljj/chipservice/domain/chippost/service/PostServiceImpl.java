package com.rljj.chipservice.domain.chippost.service;

import com.rljj.chipservice.domain.chippost.dto.PostRequest;
import com.rljj.chipservice.domain.chippost.dto.PostResponse;
import com.rljj.chipservice.domain.chippost.repository.PostRepository;
import com.rljj.switchswitchcommon.exception.NotFoundException;
import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import com.rljj.switchswitchentity.chip.chipinfo.ChipInfo;
import com.rljj.switchswitchentity.member.Member;
import com.rljj.switchswitchentity.chip.chippost.ChipPostStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Page<PostResponse> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdDate").descending());
        Page<ChipPost> posts = postRepository.findAll(pageable);
        return posts.map(PostResponse::from);
    }

    @Override
    public PostResponse getPost(Long id) {
        ChipPost post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
        return PostResponse.from(post);
    }

    @Override
    @Transactional
    public PostResponse createPost(PostRequest request) {
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

        return PostResponse.from(postRepository.save(newPost));
    }

    @Override
    @Transactional
    public PostResponse updatePost(Long chipPostId, PostRequest request) {
        ChipPost existingPost = postRepository.findById(chipPostId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID:" + chipPostId));

        existingPost.update(
                request.getTitle(),
                request.getDescription(),
                request.getStatus()
        );

        return PostResponse.from(existingPost);
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
