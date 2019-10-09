package com.ii.app.models.enums;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "conversation_statuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationStatus
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated (EnumType.STRING)
    @Column (name = "conversation_type")
    private ConversationType conversationType;

    public enum ConversationType
    {
        ACTIVE,
        RESOLVED,
        DELETED
    }
}
