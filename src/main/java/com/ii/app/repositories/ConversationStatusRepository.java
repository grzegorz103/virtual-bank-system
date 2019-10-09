package com.ii.app.repositories;

import com.ii.app.models.enums.ConversationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationStatusRepository extends JpaRepository<ConversationStatus, Long> {
    ConversationStatus findByConversationType(ConversationStatus.ConversationType conversationType);
}
