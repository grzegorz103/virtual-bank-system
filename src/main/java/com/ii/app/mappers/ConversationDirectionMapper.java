package com.ii.app.mappers;

import com.ii.app.dto.out.ConversationOut;
import com.ii.app.models.enums.ConversationDirection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConversationDirectionMapper {
    ConversationOut entityToDTO (ConversationDirection conversationDirection );
}
