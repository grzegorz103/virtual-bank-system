export class Conversation {
    id: number;
    creationDate: Date;
    topic: string;
    conversationStatus: ConversationStatus
}

export class ConversationStatus{
    id: number;
    conversationType: string;
}

export class ConversationDirection{
    id: number;
    conversationDirectionType: string;
}