package com.rljj.chipservice.domain.exchange.repository;

import com.rljj.switchswitchentity.chip.chipexchange.ChipExchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChipExchangeRepository extends JpaRepository<ChipExchange, Long> {
    List<ChipExchange> findByChipPostId(Long chipPostId);


}
