import { Injectable } from '@angular/core';
import { Message } from '../models/message';
import { HttpClient } from '@angular/common/http';
import { PageWrapper } from '../models/conversation';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  url = 'http://localhost:8080/api/messages'

  constructor(private http:HttpClient) { }

  findByConversationId(conversationId: string){
    return this.http.get<PageWrapper<Message>>(this.url + '/conversation/' + conversationId);
  }

  create(message: string){
    return this.http.post<Message>(this.url, message);
  }
}
