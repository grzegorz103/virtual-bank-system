package com.ii.app.services;

import com.ii.app.dto.in.MessageIn;
import com.ii.app.dto.out.MessageOut;
import com.ii.app.mappers.MessageMapper;
import com.ii.app.models.Message;
import com.ii.app.repositories.ConversationRepository;
import com.ii.app.repositories.MessageRepository;
import com.ii.app.repositories.UserRepository;
import com.ii.app.services.interfaces.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final UserRepository userRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository,
                              MessageMapper messageMapper,
                              ConversationRepository conversationRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MessageOut create(@NotNull MessageIn messageIn) {
        Message mapped = messageMapper.DTOtoEntity(messageIn);
        mapped.setUser(userRepository.findByIdentifier(
            SecurityContextHolder.getContext()
                .getAuthentication()
                .getName())
            .orElseThrow(() -> new RuntimeException("Not found")));
        mapped.setDate(Instant.now());
        mapped.setConversation(conversationRepository.findById(messageIn.getConversationId())
            .orElseThrow(() -> new RuntimeException("Not found")));
        return messageMapper.entityToDTO(messageRepository.save(mapped));
    }

    @Override
    public Page<MessageOut> findAllByConversationId(Pageable pageable, Long conversationId) {
        return messageRepository.findAllByConversation_Id(pageable, conversationId)
            .map(messageMapper::entityToDTO);
    }
}
