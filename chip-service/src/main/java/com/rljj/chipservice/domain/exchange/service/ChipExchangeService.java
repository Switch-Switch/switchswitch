package com.rljj.chipservice.domain.exchange.service;

import com.rljj.chipservice.domain.exchange.dto.ChipExchangeCreateRequest;
import com.rljj.chipservice.domain.exchange.dto.ChipExchangeResponse;
import com.rljj.chipservice.domain.exchange.dto.ChipExchangeStatusUpdateRequest;
import com.rljj.switchswitchentity.chip.chipexchange.ChipExchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;


public interface ChipExchangeService {
    Page<ChipExchangeResponse> getChipExchanges(Long chipPostId, Pageable pageable);

    @Transactional
    void createChipExchange(UserDetails userDetails, ChipExchangeCreateRequest request);

    @Transactional
    void updateStatus(ChipExchangeStatusUpdateRequest request);

    ChipExchange getChipExchange(Long chipExchangeId);

}
