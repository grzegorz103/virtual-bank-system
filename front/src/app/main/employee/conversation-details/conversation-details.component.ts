import { Component, OnInit } from '@angular/core';
import { Message } from '../../models/message';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from '../../services/message.service';

@Component({
  selector: 'app-conversation-details',
  templateUrl: './conversation-details.component.html',
  styleUrls: ['./conversation-details.component.scss']
})
export class ConversationDetailsComponent implements OnInit {

  messages: Message[];
  conversationId: string;

  constructor(private route: ActivatedRoute,
    private messageService: MessageService) {
    this.conversationId = this.route.snapshot.paramMap.get('id');
  }

  ngOnInit() {
    this.fetchMessages();
  }

  fetchMessages() {
    this.messageService.findByConversationId(this.conversationId)
      .subscribe(res => this.messages = res);
  }
}
