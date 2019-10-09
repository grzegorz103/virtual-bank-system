package com.ii.app.services;

import com.ii.app.dto.in.MessageIn;
import com.ii.app.dto.out.MessageOut;
import com.ii.app.mappers.MessageMapper;
import com.ii.app.models.Message;
import com.ii.app.repositories.ConversationRepository;
import com.ii.app.repositories.MessageRepository;
import com.ii.app.services.interfaces.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    private final ConversationRepository conversationRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository,
                              MessageMapper messageMapper,
                              ConversationRepository conversationRepository) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
        this.conversationRepository = conversationRepository;
    }

    @Override
    public MessageOut create(@NotNull MessageIn messageIn) {
        Message mapped = messageMapper.DTOtoEntity(messageIn);
        mapped.setDate(Instant.now());
        mapped.setConversation(conversationRepository.findById(messageIn.getConversationId())
            .orElseThrow(() -> new RuntimeException("Not found")));
        return messageMapper.entityToDTO(messageRepository.save(mapped));
    }

    @Override
    public List<MessageOut> findAllByConversationId(Long conversationId) {
        return messageRepository.findAllByConversation_Id(conversationId)
            .stream()
            .map(messageMapper::entityToDTO)
            .collect(Collectors.toList());
    }
}
