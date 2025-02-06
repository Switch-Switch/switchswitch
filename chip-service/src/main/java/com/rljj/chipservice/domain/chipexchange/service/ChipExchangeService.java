package com.rljj.chipservice.domain.chipexchange.service;

import com.rljj.chipservice.domain.chipexchange.dto.ChipExchangeCreateRequest;
import com.rljj.chipservice.domain.chipexchange.dto.ChipExchangeResponse;
import com.rljj.chipservice.domain.chipexchange.dto.ChipExchangeStatusUpdateRequest;
import com.rljj.switchswitchentity.chip.chipexchange.ChipExchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


public interface ChipExchangeService {
    Page<ChipExchangeResponse> getChipExchanges(Long chipPostId, Pageable pageable);

    @Transactional
    void createChipExchange(Long memberId, ChipExchangeCreateRequest request);

    @Transactional
    void updateStatus(ChipExchangeStatusUpdateRequest request);

    ChipExchange getChipExchange(Long chipExchangeId);

}
