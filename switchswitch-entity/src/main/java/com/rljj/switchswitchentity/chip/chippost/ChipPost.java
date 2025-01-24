package com.rljj.switchswitchentity.chip.chippost;

import com.rljj.switchswitchentity.baseentity.BaseEntity;
import com.rljj.switchswitchentity.chip.chipinfo.ChipInfo;
import com.rljj.switchswitchentity.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChipPost extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @NonNull
    private ChipInfo chipInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @NonNull
    private Member member;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @Enumerated(EnumType.STRING)
    @NonNull
    private ChipPostStatus status;

    public void update(String title, String description, String status) {
        this.title = title;
        this.description = description;
        this.status = ChipPostStatus.valueOf(status);
    }
}
