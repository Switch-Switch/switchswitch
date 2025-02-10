package com.rljj.switchswitchentity.member.dto;

import com.rljj.switchswitchentity.member.Member;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MemberDto {
    private Long memberId;
    private String nickname;

    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();
    }
}
