package com.rljj.chipservice.domain.exchange.service;

import com.rljj.chipservice.domain.exchange.dto.ChipExchangeResponse;
import com.rljj.chipservice.domain.exchange.repository.ChipExchangeRepository;
import com.rljj.switchswitchentity.chip.chipexchange.ChipExchange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ChipExchangeService {

    private final ChipExchangeRepository chipExchangeRepository;
    // TODO
//    private final ChipPostService chipPostService;

    public List<ChipExchangeResponse> getChipExchanges(Long chipPostId) {
        List<ChipExchange> chipExchanges = chipExchangeRepository.findByChipPostId(chipPostId);
        return chipExchanges.stream()
                .map(ChipExchangeResponse::from)
                .toList();
    }
}
