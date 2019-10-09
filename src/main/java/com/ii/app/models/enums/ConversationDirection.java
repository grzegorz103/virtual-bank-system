package com.ii.app.models.enums;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "conversation_direction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationDirection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "conversation_direction")
    private ConversationDirectionType conversationDirectionType;

    public enum ConversationDirectionType {
        USER_TO_EMPLOYEE,
        EMPLOYEE_TO_ADMIN
    }
}
