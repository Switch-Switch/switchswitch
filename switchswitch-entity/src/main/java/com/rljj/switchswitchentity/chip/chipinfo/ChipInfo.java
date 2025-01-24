package com.rljj.switchswitchentity.chip.chipinfo;

import com.rljj.switchswitchentity.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(indexes = {
        @Index(name = "idx_name", columnList = "name")
})
public class ChipInfo extends BaseEntity {
    @NonNull
    private String name;

    @NonNull
    private String imageUrl;

    @NonNull
    private String price;

    @Enumerated(EnumType.STRING)
    @NonNull
    private ConsoleModel consoleModel;
}
