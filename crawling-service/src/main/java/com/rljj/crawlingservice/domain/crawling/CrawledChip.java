package com.rljj.crawlingservice.domain.crawling;

import com.rljj.switchswitchentity.chip.chipinfo.Chip;
import lombok.*;
import org.jsoup.nodes.Element;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrawledChip {
    private String name;
    private String imageUrl;
    private String price;
    private String consoleModel;

    public static CrawledChip from(Element element) {
        return CrawledChip.builder()
                .name(element.select(".product-item-link").text())
                .imageUrl(element.select(".product-image-photo").attr("src"))
                .price(element.select(".price").text())
                .consoleModel("nintendo") // default
                .build();
    }

    public Chip toChip() {
        return Chip.builder()
                .name(name)
                .imageUrl(imageUrl)
                .price(price)
                .consoleModel(consoleModel)
                .createdDate(LocalDateTime.now())
                .build();
    }
}
