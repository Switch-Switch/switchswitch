package com.rljj.crawlingservice.domain.chip;

import com.rljj.switchswitchentity.chip.chipinfo.Chip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChipRepository extends JpaRepository<Chip, Long> {
    Optional<Chip> findByName(String name);
}
