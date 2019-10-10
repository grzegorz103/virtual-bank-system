import { Component, OnInit } from '@angular/core';
import { Conversation } from '../../models/conversation';
import { ConversationService } from '../../services/conversation.service';

@Component({
  selector: 'app-support-list',
  templateUrl: './support-list.component.html',
  styleUrls: ['./support-list.component.scss']
})
export class SupportListComponent implements OnInit {

  conversations: Conversation[];
  conversationColumns = ['topic', 'creationDate', 'status', 'details']

  constructor(private conversationService: ConversationService) { }

  ngOnInit() {
    this.fetchConversations();
  }

  fetchConversations(){
    this.conversationService.findEmployeeAdminConversations()
    .subscribe(res=>this.conversations = res);
  }
}
