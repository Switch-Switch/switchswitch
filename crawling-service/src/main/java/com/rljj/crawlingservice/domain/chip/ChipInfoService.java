package com.rljj.crawlingservice.domain.chip;

import com.rljj.crawlingservice.domain.crawling.CrawledChip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChipInfoService {

    private final ChipInfoRepository chipInfoRepository;

    @Transactional
    public void saveBulk(List<CrawledChip> chips) {
        chipInfoRepository.saveAll(chips.stream().map(CrawledChip::toChip).toList());
    }

    @Transactional
    public void save(CrawledChip chip) {
        chipInfoRepository.save(chip.toChip());
    }

    public long getCount() {
        return chipInfoRepository.count();
    }

    public boolean isExist(String name) {
        return chipInfoRepository.findByName(name).isPresent();
    }
}
