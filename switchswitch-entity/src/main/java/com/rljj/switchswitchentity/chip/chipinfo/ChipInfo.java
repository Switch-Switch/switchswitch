package com.rljj.switchswitchentity.chip.chipinfo;

import com.rljj.switchswitchentity.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    public static ChipInfo emptyEntity(Long id) {
        return ChipInfo.builder().id(id).build();
    }
}
