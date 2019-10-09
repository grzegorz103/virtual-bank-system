package com.ii.app.dto.out;

import com.ii.app.models.enums.ConversationStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConversationStatusOut {
    private Long id;
    private ConversationStatus.ConversationType conversationType;
}
