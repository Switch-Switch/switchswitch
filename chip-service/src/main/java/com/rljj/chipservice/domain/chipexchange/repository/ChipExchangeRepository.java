package com.rljj.chipservice.domain.chipexchange.repository;

import com.rljj.switchswitchentity.chip.chipexchange.ChipExchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChipExchangeRepository extends JpaRepository<ChipExchange, Long> {
    List<ChipExchange> findByChipPostId(Long chipPostId);

    Page<ChipExchange> findAllByChipPostId(Long chipPostId, Pageable pageable);
}
