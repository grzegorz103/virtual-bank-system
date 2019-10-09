package com.ii.app.dto.in;

import com.ii.app.models.enums.ConversationDirection;
import com.ii.app.models.enums.ConversationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationIn {
    private Long id;

    private String topic;

    private String description;

    private ConversationStatus.ConversationType conversationType;

    private ConversationDirection.ConversationDirectionType conversationDirectionType;
}
