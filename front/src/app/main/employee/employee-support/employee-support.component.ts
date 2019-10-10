import { Component, OnInit } from '@angular/core';
import { Conversation } from '../../models/conversation';
import { ConversationService } from '../../services/conversation.service';

@Component({
  selector: 'app-employee-support',
  templateUrl: './employee-support.component.html',
  styleUrls: ['./employee-support.component.scss']
})
export class EmployeeSupportComponent implements OnInit {

  userEmployeeColumns = ['topic', 'creationDate', 'status', 'details']
  userConversations: Conversation[];

  isLoadingUserConversations = true;
  isLoadingMyConversations = true;
  
  myConversationColumns = ['topic', 'creationDate', 'status', 'details']
  myConversations: Conversation[];

  constructor(private conversationService: ConversationService) { }

  ngOnInit() {
    this.fetchUserConversations();
    this.fetchMyConversations();
  }

  fetchUserConversations() {
    this.conversationService.findUserEmployeeConversations()
      .subscribe(res => { this.userConversations = res; this.isLoadingUserConversations = false });
  }

  fetchMyConversations(){
    this.conversationService.findByUser()
    .subscribe(res=>{this.myConversations = res; this.isLoadingMyConversations = false});
  }

}
