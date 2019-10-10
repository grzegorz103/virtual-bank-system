import { Injectable } from '@angular/core';
import { Message } from '../models/message';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  url = 'http://localhost:8080/api/messages'

  constructor(private http:HttpClient) { }

  findByConversationId(conversationId: string){
    return this.http.get<Message[]>(this.url + '/conversation/' + conversationId);
  }
}
