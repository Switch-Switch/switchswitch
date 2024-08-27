package com.rljj.switchswitchcrawling.domain.crawling;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class CrawlingNintendoStore implements CrawlingRunner {

    @Value("${crawling.product-list-limit}")
    private int productListLimit;

    /**
     * 크롤링 후 CrawledChip 객체로 변환
     *
     * @param url 페이지 파라미터가 포함된 url
     * @return 칩 데이터 DTO
     * @throws IOException 크롤링 실패
     */
    @Override
    public List<CrawledChip> crawl(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        return document.getElementsByClass("item product product-item")
                .stream()
                .map(CrawledChip::from)
                .collect(Collectors.toList());
    }

    /**
     * 전체 페이지 사이즈 가져오는 메서드
     *
     * @return 페이지 사이즈
     * @throws IOException 크롤링 실패
     */
    @Override
    public int getPageSize(String url) throws IOException {
        double totalItemCount = getTotalItemSize(url);
        return (int) Math.ceil(totalItemCount / productListLimit);
    }

    /**
     * 전체 상품 개수 가져오는 메서드
     *
     * @return 상품 개수
     * @throws IOException 크롤링 실패
     */
    @Override
    public int getTotalItemSize(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Element element = document.getElementById("toolbar-amount");
        return Integer.parseInt(Objects.requireNonNull(element).text().split(" ")[2]);
    }

    @Override
    public String getUrlPageParameterKey() {
        return "p";
    }
}