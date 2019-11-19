import { User } from './user';

export class Conversation {
    id: number;
    creationDate: Date;
    topic: string;
    user: User;
    conversationStatus: ConversationStatus
}

export class ConversationStatus {
    id: number;
    conversationType: string;
}

export class ConversationDirection {
    id: number;
    conversationDirectionType: string;
}

export class PageWrapper<T>{
    content: T[];
    totalPages: number;
    totalElements: number;
    last: boolean;
    size: number;
    numberOfElements: number;
}