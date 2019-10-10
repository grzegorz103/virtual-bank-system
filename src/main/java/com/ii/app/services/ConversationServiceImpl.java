package com.ii.app.services;

import com.ii.app.dto.in.ConversationIn;
import com.ii.app.dto.in.MessageIn;
import com.ii.app.dto.out.ConversationOut;
import com.ii.app.dto.out.MessageOut;
import com.ii.app.mappers.ConversationMapper;
import com.ii.app.models.Conversation;
import com.ii.app.models.enums.ConversationDirection;
import com.ii.app.models.enums.ConversationStatus;
import com.ii.app.models.user.User;
import com.ii.app.repositories.ConversationDirectionRepository;
import com.ii.app.repositories.ConversationRepository;
import com.ii.app.repositories.ConversationStatusRepository;
import com.ii.app.repositories.UserRepository;
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

    private final ConversationDirectionRepository conversationDirectionRepository;

    private final ConversationStatusRepository conversationStatusRepository;

    @Autowired
    public ConversationServiceImpl(ConversationRepository conversationRepository,
                                   ConversationMapper conversationMapper,
                                   UserRepository userRepository,
                                   ConversationDirectionRepository conversationDirectionRepository,
                                   ConversationStatusRepository conversationStatusRepository) {
        this.conversationRepository = conversationRepository;
        this.conversationMapper = conversationMapper;
        this.userRepository = userRepository;
        this.conversationDirectionRepository = conversationDirectionRepository;
        this.conversationStatusRepository = conversationStatusRepository;
    }

    @Override
    public ConversationOut create(@NotNull ConversationIn conversationIn) {
        Conversation mapped = conversationMapper.DTOtoEntity(conversationIn);
        mapped.setMessages(new HashSet<>());
        mapped.setUser(userRepository.findByIdentifier(
            SecurityContextHolder.getContext()
                .getAuthentication()
                .getName())
            .orElseThrow(() -> new RuntimeException("Not found")));
        mapped.setCreationDate(Instant.now());
        mapped.setConversationDirection(conversationDirectionRepository.findByConversationDirectionType(
            conversationIn.getConversationDirectionType())
        );
        mapped.setConversationStatus(conversationStatusRepository.findByConversationType(
            conversationIn.getConversationType()
        ));
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
        User currentUser = userRepository.findByIdentifier(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new RuntimeException("User not fuond"));
        return conversationRepository.findAllByUser(currentUser)
            .stream()
            .map(conversationMapper::entityToDTO)
            .collect(Collectors.toList());
    }
}
