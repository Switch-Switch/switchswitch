package com.rljj.chipservice.domain.chippost.dto;

import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChipPostResponse {
    private Long id;
    private Long chipInfoId;
    private Long memberId;
    private String title;
    private String description;
    private String status;

    public static ChipPostResponse from(ChipPost chipPost) {
        return new ChipPostResponse(
                chipPost.getId(),
                chipPost.getChipInfo().getId(),
                chipPost.getMember().getId(),
                chipPost.getTitle(),
                chipPost.getDescription(),
                chipPost.getStatus().name());
    }
}