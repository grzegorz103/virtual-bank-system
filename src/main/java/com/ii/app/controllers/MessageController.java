package com.ii.app.controllers;

import com.ii.app.dto.in.MessageIn;
import com.ii.app.dto.out.MessageOut;
import com.ii.app.models.Message;
import com.ii.app.services.interfaces.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/conversation/{id}")
    @PreAuthorize("isAuthenticated()")
    public List<MessageOut> findByConversationId(@PathVariable("id") Long id) {
        return messageService.findAllByConversationId(id);
    }

    @PostMapping
    @Secured("isAuthenticated()")
    public MessageOut create(@RequestBody MessageIn messageIn) {
        return messageService.create(messageIn);
    }
}
