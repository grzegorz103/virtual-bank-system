import {Injectable} from '@angular/core';
import {Conversation} from '../models/conversation';
import {HttpClient} from '@angular/common/http';
import {environment} from "src/environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ConversationService {

  url = environment.apiUrl + '/api/conversations';

  constructor(private http: HttpClient) {
  }

  findUserEmployeeConversations() {
    return this.http.get<Conversation[]>(this.url + '/userToEmployee');
  }

  findByUser() {
    return this.http.get<Conversation[]>(this.url + '/my');
  }

  findEmployeeAdminConversations() {
    return this.http.get<Conversation[]>(this.url + '/employeeToAdmin');
  }

  create(conversation: string) {
    return this.http.post<Conversation>(this.url, conversation);
  }

  findById(id: string) {
    return this.http.get<Conversation>(this.url + '/id/' + id);
  }

  changeStatus(id: number) {
    return this.http.patch<Conversation>(this.url + '/' + id, null);
  }
}
