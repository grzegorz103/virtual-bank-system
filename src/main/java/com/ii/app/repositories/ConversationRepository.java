package com.ii.app.repositories;

import com.ii.app.models.Conversation;
import com.ii.app.models.enums.ConversationDirection;
import com.ii.app.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findAllByConversationDirection(ConversationDirection conversationDirection);

    List<Conversation> findAllByUser(User user);
}