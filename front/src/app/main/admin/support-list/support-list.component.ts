import { Component, OnInit, ViewChild } from '@angular/core';
import { Conversation } from '../../models/conversation';
import { ConversationService } from '../../services/conversation.service';
import { MatTableDataSource, MatPaginator } from '@angular/material';

@Component({
  selector: 'app-support-list',
  templateUrl: './support-list.component.html',
  styleUrls: ['./support-list.component.scss']
})
export class SupportListComponent implements OnInit {
  isLoadingConversations: any;
  conversations = new MatTableDataSource<Conversation>();
  
  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;
  
  conversationColumns = ['topic', 'creationDate', 'status', 'details']

  constructor(private conversationService: ConversationService) { }

  ngOnInit() {
    this.fetchConversations();
  }

  fetchConversations(){
    this.conversationService.findEmployeeAdminConversations()
    .subscribe(res=>{this.conversations.data = res;
      this.conversations.paginator = this.paginator;});
  }

  changeStatus(id: number) {
    this.conversationService.changeStatus(id)
      .subscribe(res => this.fetchConversations());
  }
}
