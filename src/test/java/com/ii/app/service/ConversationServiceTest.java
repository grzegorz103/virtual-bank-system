package com.ii.app.service;

import com.ii.app.AppApplication;
import com.ii.app.models.Conversation;
import com.ii.app.repositories.ConversationRepository;
import com.ii.app.repositories.UserRepository;
import com.ii.app.services.interfaces.ConversationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(classes = AppApplication.class)
public class ConversationServiceTest {

    @Autowired
    private ConversationRepository conversationRepository;

    @SpyBean
    private ConversationService conversationService;

    @Test
    public void findAllTest(){
        assertThat(conversationService.findAll().size()).isEqualTo(conversationRepository.findAll().size());
    }
}
