package com.ii.app.mappers;

import com.ii.app.dto.in.ConversationIn;
import com.ii.app.dto.out.ConversationOut;
import com.ii.app.models.Conversation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses={ConversationDirectionMapper.class, ConversationStatusMapper.class})
    public interface ConversationMapper {
    Conversation DTOtoEntity (ConversationIn conversationIn );
    ConversationOut entityToDTO (Conversation conversation );
}
