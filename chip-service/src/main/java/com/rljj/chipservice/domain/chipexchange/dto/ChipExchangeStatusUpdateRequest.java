package com.rljj.chipservice.domain.chipexchange.dto;

import com.rljj.switchswitchentity.chip.chipexchange.ChipExchangeStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChipExchangeStatusUpdateRequest {
    @NotBlank
    private Long chipExchangeId;

    @NotBlank
    private ChipExchangeStatus status;
}
