package com.rljj.chipservice.domain.post.repository;

import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<ChipPost, Long> {
}