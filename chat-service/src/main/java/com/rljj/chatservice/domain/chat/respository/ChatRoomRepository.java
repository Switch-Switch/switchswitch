package com.rljj.chatservice.domain.chat.respository;

import com.rljj.switchswitchentity.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT c FROM ChatRoom c " +
            "JOIN FETCH c.author " +
            "JOIN FETCH c.requester " +
            "JOIN FETCH c.chipPost " +
            "WHERE c.author.id = :memberId OR c.requester.id = :memberId")
    List<ChatRoom> findChatRoomsByMemberId(@Param("memberId") Long memberId);

}
