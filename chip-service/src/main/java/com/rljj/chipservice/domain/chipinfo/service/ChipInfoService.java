package com.rljj.chipservice.domain.chipinfo.service;

import com.rljj.chipservice.domain.chipinfo.dto.ChipInfoResponse;

public interface ChipInfoService {
    ChipInfoResponse getChipInfo(Long chipInfoId);
}
