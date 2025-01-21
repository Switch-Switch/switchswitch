package com.rljj.switchswitchentity.chat;

import com.rljj.switchswitchentity.baseentity.BaseEntity;
import com.rljj.switchswitchentity.chip.chippost.ChipPost;
import com.rljj.switchswitchentity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat_room")
public class ChatRoom extends BaseEntity {
//    @Id
//    @Column(name = "chat_room_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @ManyToOne
    @JoinColumn(name = "chip_post_id", nullable = false)
    private ChipPost post;

    @ManyToOne
    @JoinColumn(name = "creator_user_id", nullable = false)
    private Member creatorUser;

    @ManyToOne
    @JoinColumn(name = "interested_user_id", nullable = false)
    private Member interestedUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ChatRoomStatus status;
    
}
