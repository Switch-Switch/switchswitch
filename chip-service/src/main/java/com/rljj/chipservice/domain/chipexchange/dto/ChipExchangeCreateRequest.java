package com.rljj.chipservice.domain.chipexchange.dto;

import com.rljj.switchswitchentity.chip.chipexchange.ChipExchangeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChipExchangeCreateRequest {
    private Long chipPostId;
    private Long chipInfoId;
    private String content;
    private ChipExchangeStatus status;
}
