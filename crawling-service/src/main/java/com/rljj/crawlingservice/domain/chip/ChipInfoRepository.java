package com.rljj.crawlingservice.domain.chip;

import com.rljj.switchswitchentity.chip.chipinfo.ChipInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChipInfoRepository extends JpaRepository<ChipInfo, Long> {
    Optional<ChipInfo> findByName(String name);
}
