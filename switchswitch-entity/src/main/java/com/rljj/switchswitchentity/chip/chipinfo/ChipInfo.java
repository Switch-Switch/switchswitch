package com.rljj.switchswitchentity.chip.chipinfo;

import com.rljj.switchswitchentity.baseentity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String name;

    @NotNull
    private String imageUrl;

    @NotNull
    private String price;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ConsoleModel consoleModel;

    public static ChipInfo emptyEntity(Long id) {
        return ChipInfo.builder().id(id).build();
    }
}
