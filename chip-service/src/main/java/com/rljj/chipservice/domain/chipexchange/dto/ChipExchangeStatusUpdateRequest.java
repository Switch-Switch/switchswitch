package com.rljj.chipservice.domain.chipexchange.dto;

import com.rljj.switchswitchentity.chip.chipexchange.ChipExchangeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChipExchangeStatusUpdateRequest {
    private Long chipExchangeId;
    private ChipExchangeStatus status;
}
