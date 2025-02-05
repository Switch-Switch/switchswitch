package com.rljj.chipservice.domain.chippost.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long chipInfoId;
    private Long memberId;
    private String title;
    private String description;
    private String status;
}