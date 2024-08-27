package com.rljj.switchswitchcrawling.domain.chip;

import com.rljj.switchswitchcrawling.domain.crawling.CrawledChip;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chip", indexes = {
        @Index(name = "idx_name", columnList = "name")
})
public class Chip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    private String price;

    private String consoleModel;

    private LocalDateTime createdDate;

    public static Chip from(CrawledChip chip) {
        return Chip.builder()
                .name(chip.getName())
                .imageUrl(chip.getImageUrl())
                .price(chip.getPrice())
                .consoleModel(chip.getConsoleModel())
                .createdDate(LocalDateTime.now())
                .build();
    }
}
