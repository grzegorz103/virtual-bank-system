package com.ii.app.dto.out;

import com.ii.app.models.enums.ConversationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationOut {
    private Long id;
    private Instant creationDate;
    private String topic;
    private UserOut user;
    private ConversationStatusOut conversationStatus;
    private ConversationDirectionOut conversationDirection;
}
