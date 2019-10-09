package com.ii.app.mappers;

import com.ii.app.dto.in.ConversationIn;
import com.ii.app.dto.in.MessageIn;
import com.ii.app.dto.out.ConversationOut;
import com.ii.app.dto.out.MessageOut;
import com.ii.app.models.Conversation;
import com.ii.app.models.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message DTOtoEntity(MessageIn messageIn);

    MessageOut entityToDTO(Message message);
}
