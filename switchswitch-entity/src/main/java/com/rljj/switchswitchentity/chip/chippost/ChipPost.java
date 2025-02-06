package com.rljj.switchswitchentity.chip.chippost;

import com.rljj.switchswitchentity.baseentity.BaseEntity;
import com.rljj.switchswitchentity.chip.chipinfo.ChipInfo;
import com.rljj.switchswitchentity.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class ChipPost extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private ChipInfo chipInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Member member;

    @NotNull
    @Column(nullable = false)
    private String title;

    @NotNull
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ChipPostStatus status;

    public void update(String title, String description, ChipPostStatus status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public static ChipPost of(Long id) {
        return ChipPost.builder().id(id).build();
    }
}
