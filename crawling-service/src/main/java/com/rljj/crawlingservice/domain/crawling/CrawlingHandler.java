package com.rljj.crawlingservice.domain.crawling;

import com.rljj.crawlingservice.domain.chip.ChipInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class CrawlingHandler {

    private final CrawlingRunner crawlingRunner;
    private final ChipInfoService chipInfoService;

    @Value("${crawling.baseUrl}")
    private String baseUrl;

    /**
     * 스케줄링 메서드 <br/><br/>
     * 기본: 새벽 3시에 작업 시작
     */
//    바로 실행해보고 싶으면 fixedRate로 설정
//    @Scheduled(fixedRate = 1000000)
    @Scheduled(cron = "0 0 3 * * ?")
    public void crawl() {
//        if (isFirst()) initialize();
//        if (isOutdated()) update();
    }

    private boolean isFirst() {
        return chipInfoService.getCount() == 0;
    }

    private void initialize() {
        log.info("Starts the crawl initialization. URL: {}", baseUrl);
        try {
            int pageSize = getPageSize();
            String url = getUrlWithPage();

            for (int i = pageSize; i > 0; i--) { // 오래된 순부터
                List<CrawledChip> chips = crawlingRunner.crawl(url + i);
                chipInfoService.saveBulk(chips);
                sleepZZ(i);
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            log.info("Terminates the crawl initialization.");
        }
    }

    private boolean isOutdated() {
        try {
            return chipInfoService.getCount() < crawlingRunner.getTotalItemSize(baseUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void update() {
        log.info("Start the crawl update. URL: {}", baseUrl);
        try {
            int pageSize = getPageSize();
            String url = getUrlWithPage();

            for (int i = 1; i <= pageSize; i++) { // 최신 순부터
                List<CrawledChip> chips = crawlingRunner.crawl(url + i);
                for (CrawledChip chip : chips) {
                    if (isExist(chip.getName())) return;
                    chipInfoService.save(chip);
                }
                sleepZZ(i);
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            log.info("Terminates the crawl update.");
        }
    }

    private int getPageSize() throws IOException {
        int pageSize = crawlingRunner.getPageSize(baseUrl);
        log.info("pageSize: {}", pageSize);
        return pageSize;
    }

    private boolean isExist(String name) {
        return chipInfoService.isExist(name);
    }

    private void sleepZZ(int page) throws InterruptedException {
        log.info("Thread sleep, current page: {}", page);
        Thread.sleep(2000);
    }

    private String getUrlWithPage() {
        return baseUrl + "&" + crawlingRunner.getUrlPageParameterKey() + "=";
    }
}
