package com.rljj.chipservice.domain.chipexchange.dto;

import com.rljj.switchswitchentity.chip.chipexchange.ChipExchangeStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChipExchangeCreateRequest {
    @NotBlank
    private Long chipPostId;

    @NotBlank
    private Long chipInfoId;

    private String content;

    @NotBlank
    private ChipExchangeStatus status;
}
