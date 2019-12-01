package com.ii.app.dto.in;

import com.ii.app.models.enums.ConversationDirection;
import com.ii.app.models.enums.ConversationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationIn {
    private Long id;

    @NotEmpty
    @Length(min = 1, max = 255)
    private String topic;

    @NotEmpty
    @Length(min = 1, max = 255)
    private String description;

    @NotNull
    private ConversationStatus.ConversationType conversationType;

    @NotNull
    private ConversationDirection.ConversationDirectionType conversationDirectionType;
}
