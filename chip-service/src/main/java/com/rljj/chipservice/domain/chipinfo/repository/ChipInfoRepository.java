package com.rljj.chipservice.domain.chipinfo.repository;

import com.rljj.switchswitchentity.chip.chipinfo.ChipInfo;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChipInfoRepository extends JpaRepository<ChipInfo, Long> {
    Optional<ChipInfo> findById(Long chipInfoId);
}
