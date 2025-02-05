package com.rljj.chipservice.domain.chipinfo.service;

import com.rljj.chipservice.domain.chipinfo.dto.ChipInfoResponse;
import com.rljj.chipservice.domain.chipinfo.repository.ChipInfoRepository;
import com.rljj.switchswitchcommon.exception.NotFoundException;
import com.rljj.switchswitchentity.chip.chipinfo.ChipInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChipInfoServiceImpl implements ChipInfoService {

    private final ChipInfoRepository chipInfoRepository;

    @Override
    @Transactional(readOnly = true)
    public ChipInfoResponse getChipInfo(Long chipInfoId) {
        ChipInfo chipInfo = chipInfoRepository.findById(chipInfoId)
                .orElseThrow(() -> new NotFoundException("Not Found ChipInfo: " + chipInfoId));
        return ChipInfoResponse.from(chipInfo);
    }
}
