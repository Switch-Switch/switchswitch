package com.rljj.switchswitchcrawling.domain.crawling;

import java.io.IOException;
import java.util.List;

public interface CrawlingRunner {
    List<CrawledChip> crawl(String url) throws IOException;

    int getPageSize(String url) throws IOException;

    int getTotalItemSize(String url) throws IOException;

    String getUrlPageParameterKey();
}
