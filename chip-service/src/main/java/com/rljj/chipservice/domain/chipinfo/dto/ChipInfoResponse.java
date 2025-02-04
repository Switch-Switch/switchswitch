package com.rljj.chipservice.domain.chipinfo.dto;

import com.rljj.switchswitchentity.chip.chipinfo.ChipInfo;
import com.rljj.switchswitchentity.chip.chipinfo.ConsoleModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class ChipInfoResponse {
    private String name;
    private String imageUrl;
    private String price;
    private ConsoleModel consoleModel;

    public static ChipInfoResponse from(ChipInfo chipInfo) {
        return ChipInfoResponse.builder()
                .name(chipInfo.getName())
                .imageUrl(chipInfo.getImageUrl())
                .price(chipInfo.getPrice())
                .consoleModel(chipInfo.getConsoleModel())
                .build();
    }
}
