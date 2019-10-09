package com.ii.app.mappers;

import com.ii.app.dto.out.BankAccTypeOut;
import com.ii.app.dto.out.ConversationStatusOut;
import com.ii.app.models.BankAccType;
import com.ii.app.models.enums.ConversationStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConversationStatusMapper {
    ConversationStatusOut entityToDto(ConversationStatus conversationStatus);
}
