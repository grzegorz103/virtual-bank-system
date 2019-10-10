package com.ii.app.services;

import com.ii.app.dto.in.ConversationIn;
import com.ii.app.dto.in.MessageIn;
import com.ii.app.dto.out.ConversationOut;
import com.ii.app.dto.out.MessageOut;
import com.ii.app.mappers.ConversationMapper;
import com.ii.app.models.Conversation;
import com.ii.app.models.Message;
import com.ii.app.models.enums.ConversationDirection;
import com.ii.app.models.enums.ConversationStatus;
import com.ii.app.models.user.User;
import com.ii.app.repositories.*;
import com.ii.app.services.interfaces.ConversationService;
import com.ii.app.services.interfaces.MessageService;
import com.ii.app.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationServiceImpl implements ConversationService {
    private final ConversationRepository conversationRepository;

    private final ConversationMapper conversationMapper;

    private final UserRepository userRepository;

    private final MessageRepository messageRepository;

    private final ConversationDirectionRepository conversationDirectionRepository;

    private final ConversationStatusRepository conversationStatusRepository;

    @Autowired
    public ConversationServiceImpl(ConversationRepository conversationRepository,
                                   ConversationMapper conversationMapper,
                                   UserRepository userRepository,
                                   ConversationDirectionRepository conversationDirectionRepository,
                                   ConversationStatusRepository conversationStatusRepository,
                                   MessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.conversationMapper = conversationMapper;
        this.userRepository = userRepository;
        this.conversationDirectionRepository = conversationDirectionRepository;
        this.conversationStatusRepository = conversationStatusRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public ConversationOut create(@NotNull ConversationIn conversationIn) {
        Conversation mapped = conversationMapper.DTOtoEntity(conversationIn);
        User currentUser = userRepository.findByIdentifier(
            SecurityContextHolder.getContext()
                .getAuthentication()
                .getName())
            .orElseThrow(() -> new RuntimeException("Not found"));

        mapped.setMessages(new HashSet<>());
        mapped.setUser(currentUser);
        mapped.setCreationDate(Instant.now());
        mapped.setConversationDirection(conversationDirectionRepository.findByConversationDirectionType(
            conversationIn.getConversationDirectionType())
        );
        mapped.setConversationStatus(conversationStatusRepository.findByConversationType(
            conversationIn.getConversationType()
        ));

        conversationRepository.save(mapped);

        messageRepository.save(
            Message.builder()
                .conversation(mapped)
                .date(Instant.now())
                .user(currentUser)
                .message(conversationIn.getDescription())
                .build()
        );

        return conversationMapper.entityToDTO(conversationRepository.save(mapped));
    }

    @Override
    public List<ConversationOut> findAll() {
        return conversationRepository.findAll()
            .stream()
            .map(conversationMapper::entityToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<ConversationOut> findByConversationDirection(ConversationDirection.ConversationDirectionType conversationDirectionType) {
        ConversationDirection conversationDirection = conversationDirectionRepository.findByConversationDirectionType(conversationDirectionType);
        return conversationRepository.findAllByConversationDirection(conversationDirection)
            .stream()
            .map(conversationMapper::entityToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<ConversationOut> findByCurrentUser() {
        User currentUser = userRepository.findByIdentifier(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() -> new RuntimeException("User not fuond"));
        return conversationRepository.findAllByUser(currentUser)
            .stream()
            .map(conversationMapper::entityToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public ConversationOut findById(Long id) {
        return conversationMapper.entityToDTO(conversationRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found")));
    }

    @Override
    public ConversationOut changeStatus(Long id) {
        Conversation conversation = conversationRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));

        switch (conversation.getConversationStatus().getConversationType()) {
            case ACTIVE:
                conversation.setConversationStatus(conversationStatusRepository.findByConversationType(ConversationStatus.ConversationType.RESOLVED));
                break;
            case RESOLVED:
                conversation.setConversationStatus(conversationStatusRepository.findByConversationType(ConversationStatus.ConversationType.ACTIVE));
                break;
        }

        return conversationMapper.entityToDTO(conversationRepository.save(conversation));
    }
}
