package com.rljj.chipservice.domain.chippost.service;

import com.rljj.chipservice.domain.chipinfo.service.ChipInfoService;
import com.rljj.chipservice.domain.chippost.dto.ChipPostRequest;
import com.rljj.chipservice.domain.chippost.dto.ChipPostResponse;
import com.rljj.chipservice.domain.chippost.repository.ChipPostRepository;
import com.rljj.switchswitchcommon.exception.NotFoundException;
import com.rljj.switchswitchentity.chip.chipinfo.ChipInfo;
import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import com.rljj.switchswitchentity.chip.chippost.ChipPostStatus;
import com.rljj.switchswitchentity.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChipPostServiceImpl implements ChipPostService {

    private final ChipPostRepository postRepository;
    private final ChipInfoService chipInfoService;

    @Override
    public Page<ChipPostResponse> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdDate").descending());
        Page<ChipPost> posts = postRepository.findAll(pageable);
        return posts.map(ChipPostResponse::from);
    }

    @Override
    public ChipPostResponse getPost(Long id) {
        ChipPost post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
        return ChipPostResponse.from(post);
    }

    @Override
    @Transactional
    public ChipPostResponse createPost(ChipPostRequest request) {
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

        return ChipPostResponse.from(postRepository.save(newPost));
    }

    @Override
    @Transactional
    public ChipPostResponse updatePost(Long chipPostId, ChipPostRequest request) {
        ChipPost existingPost = postRepository.findById(chipPostId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID:" + chipPostId));

        existingPost.update(request.getTitle(), request.getDescription(), request.getStatus());

        return ChipPostResponse.from(existingPost);
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
