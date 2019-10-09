package com.ii.app.services.interfaces;

import com.ii.app.dto.in.MessageIn;
import com.ii.app.dto.out.MessageOut;

import java.util.List;

public interface MessageService {
    MessageOut create(MessageIn messageIn);

    List<MessageOut> findAllByConversationId(Long conversationId);
}
