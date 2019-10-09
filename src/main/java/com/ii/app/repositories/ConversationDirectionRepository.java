package com.ii.app.repositories;

import com.ii.app.models.enums.ConversationDirection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationDirectionRepository extends JpaRepository<ConversationDirection, Long> {
    ConversationDirection findByConversationDirectionType(ConversationDirection.ConversationDirectionType conversationDirectionType);
}
