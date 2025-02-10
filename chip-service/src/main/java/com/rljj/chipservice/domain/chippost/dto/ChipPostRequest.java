package com.rljj.chipservice.domain.chippost.dto;

import com.rljj.switchswitchentity.chip.chippost.ChipPostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChipPostRequest {
    @NotBlank
    private Long chipInfoId;

    @NotBlank
    @Size(min = 1, max = 30)
    private String title;

    @NotBlank
    @Size(min = 2, max = 1000)
    private String description;

    @NotBlank
    private ChipPostStatus status;
}