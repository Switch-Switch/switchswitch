package com.rljj.chipservice.domain.chippost.dto;

import com.rljj.chipservice.domain.chipinfo.dto.ChipInfoResponse;
import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import com.rljj.switchswitchentity.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChipPostResponse {
    private Long id;
    private ChipInfoResponse chipInfo;
    private MemberDto member;
    private String title;
    private String description;
    private String status;

    public static ChipPostResponse from(ChipPost chipPost) {
        return new ChipPostResponse(
                chipPost.getId(),
                ChipInfoResponse.from(chipPost.getChipInfo()),
                MemberDto.from(chipPost.getMember()),
                chipPost.getTitle(),
                chipPost.getDescription(),
                chipPost.getStatus().name());
    }
}