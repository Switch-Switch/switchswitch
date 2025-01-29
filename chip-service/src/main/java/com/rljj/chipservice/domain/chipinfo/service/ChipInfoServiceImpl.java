package com.rljj.chipservice.domain.chipinfo.service;

import com.rljj.chipservice.domain.chipinfo.dto.ChipInfoResponse;
import com.rljj.chipservice.domain.chipinfo.repository.ChipInfoRepository;
import com.rljj.switchswitchcommon.exception.NotFoundException;
import com.rljj.switchswitchentity.chip.chipinfo.ChipInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChipInfoServiceImpl implements ChipInfoService {

    private final ChipInfoRepository chipInfoRepository;

    @Override
    public ChipInfoResponse getChipInfo(String chipName) {
        ChipInfo chipInfo = chipInfoRepository.findByName(chipName)
                .orElseThrow(() -> new NotFoundException("Not Found ChipInfo: " + chipName));
        return ChipInfoResponse.from(chipInfo);
    }

}
