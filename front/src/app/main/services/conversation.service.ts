import { Injectable } from '@angular/core';
import { Conversation } from '../models/conversation';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ConversationService {

  url ='http://localhost:8080/api/conversations';

  constructor(private http: HttpClient) { }

  findUserEmployeeConversations(){
    return this.http.get<Conversation[]>(this.url + '/userToEmployee');
  }

  findByUser(){
    return this.http.get<Conversation[]>(this.url + '/my');
  }
  
  findEmployeeAdminConversations(){
    return this.http.get<Conversation[]>(this.url + '/employeeToAdmin');
  }

  create(conversation: string){
    return this.http.post<Conversation>(this.url, conversation);
  }
}
