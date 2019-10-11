package com.ii.app.services.interfaces;

import com.ii.app.dto.in.MessageIn;
import com.ii.app.dto.out.MessageOut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    MessageOut create(MessageIn messageIn);

    Page<MessageOut> findAllByConversationId(Pageable pageable, Long conversationId);
}
