package com.rljj.switchswitchentity.chip;

import com.rljj.switchswitchentity.baseentity.BaseEntity;
import com.rljj.switchswitchentity.chip.type.ConsoleModel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chip_info")
public class ChipInfo extends BaseEntity {
    @Id
    @Column(name = "chip_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "price", nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(name = "console_model", nullable = false)
    private ConsoleModel consoleModel;

    // 임시로 만든 builder 예시
    @Builder
    public ChipInfo(String name, String imageUrl, int price, ConsoleModel consoleModel) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.consoleModel = consoleModel;
    }

}
