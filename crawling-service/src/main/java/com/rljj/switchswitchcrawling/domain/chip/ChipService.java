package com.rljj.switchswitchcrawling.domain.chip;

import com.rljj.switchswitchcrawling.domain.crawling.CrawledChip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChipService {

    private final ChipRepository chipRepository;

    @Transactional
    public void saveBulk(List<CrawledChip> chips) {
        chipRepository.saveAll(chips.stream().map(Chip::from).toList());
    }

    @Transactional
    public void save(CrawledChip chip) {
        chipRepository.save(Chip.from(chip));
    }

    public long getCount() {
        return chipRepository.count();
    }

    public boolean isExist(String name) {
        return chipRepository.findByName(name).isPresent();
    }
}
