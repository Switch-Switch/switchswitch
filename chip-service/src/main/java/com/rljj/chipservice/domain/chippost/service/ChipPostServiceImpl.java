package com.rljj.chipservice.domain.chippost.service;

import com.rljj.chipservice.domain.chippost.dto.ChipPostRequest;
import com.rljj.chipservice.domain.chippost.dto.ChipPostResponse;
import com.rljj.chipservice.domain.chippost.repository.ChipPostRepository;
import com.rljj.switchswitchcommon.exception.NotFoundException;
import com.rljj.switchswitchentity.chip.chipinfo.ChipInfo;
import com.rljj.switchswitchentity.chip.chippost.ChipPost;
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

    @Override
    public Page<ChipPostResponse> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdDate").descending());
        Page<ChipPost> posts = postRepository.findAll(pageable);
        return posts.map(ChipPostResponse::from);
    }

    @Override
    public ChipPostResponse getPost(Long chipPostId) {
        ChipPost post = getChipPost(chipPostId);
        return ChipPostResponse.from(post);
    }

    @Override
    @Transactional
    public ChipPostResponse createPost(ChipPostRequest request) {
        Member member = Member.of(request.getMemberId());
        ChipInfo chipInfo = ChipInfo.of(request.getChipInfoId());

        ChipPost newPost = ChipPost.builder()
                .chipInfo(chipInfo)
                .member(member)
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .build();

        return ChipPostResponse.from(postRepository.save(newPost));
    }

    @Override
    @Transactional
    public ChipPostResponse updatePost(Long chipPostId, ChipPostRequest request) {
        ChipPost existingPost = getChipPost(chipPostId);

        existingPost.update(request.getTitle(), request.getDescription(), request.getStatus());

        return ChipPostResponse.from(existingPost);
    }

    @Override
    @Transactional
    public void deletePost(Long chipPostId) {
        if (!postRepository.existsById(chipPostId)) {
            throw new NotFoundException(String.valueOf(chipPostId));
        }
        postRepository.deleteById(chipPostId);
    }

    private ChipPost getChipPost(Long chipPostId) {
        return postRepository.findById(chipPostId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID:" + chipPostId));
    }
}
