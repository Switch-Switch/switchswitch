package com.rljj.chipservice.domain.chipinfo.controller;

import com.rljj.chipservice.domain.chipinfo.dto.ChipInfoResponse;
import com.rljj.chipservice.domain.chipinfo.service.ChipInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/api/chip-info")
public class ChipInfoController {

    private final ChipInfoService chipInfoService;

    @GetMapping("/{chipInfoId}")
    public ChipInfoResponse getChipInfo(@PathVariable Long chipInfoId) {
        return chipInfoService.getChipInfo(chipInfoId);
    }
}
