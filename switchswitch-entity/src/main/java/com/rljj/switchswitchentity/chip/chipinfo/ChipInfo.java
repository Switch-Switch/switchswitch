package com.rljj.switchswitchentity.chip.chipinfo;

import com.rljj.switchswitchentity.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "chip_info")
public class ChipInfo extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "price", nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(name = "console_model", nullable = false)
    private ConsoleModel consoleModel;
}
