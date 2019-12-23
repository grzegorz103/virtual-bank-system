import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators, NgForm } from '@angular/forms';
import { AuthService } from 'src/app/shared/services/auth.service';
import { MatSnackBar } from '@angular/material';
import { Conversation } from '../../models/conversation';
import { ConversationService } from '../../services/conversation.service';
import { MessageService } from '../../services/message.service';
import { Message } from '../../models/message';

@Component({
  selector: 'app-conversation-details',
  templateUrl: './conversation-details.component.html',
  styleUrls: ['./conversation-details.component.scss']
})
export class ConversationDetailsComponent implements OnInit {

  messages: Message[];
  conversationId: string;
  replyForm: FormGroup;
  conversation: Conversation;
  currentPage = 0;
  totalElementCount = 0;
  isLoading = false;

  @ViewChild('formDirective', { static: true }) private formDirective: NgForm;

  constructor(private route: ActivatedRoute,
    private fb: FormBuilder,
    public authService: AuthService,
    private snackBar: MatSnackBar,
    private conversationService: ConversationService,
    private messageService: MessageService) {
    this.conversationId = this.route.snapshot.paramMap.get('id');
  }

  ngOnInit() {
    this.fetchConversation();
    this.fetchMessages();
    this.createReplyForm();
  }

  fetchConversation() {
    this.conversationService.findById(this.conversationId)
      .subscribe(res => this.conversation = res);
  }

  fetchMessages() {
    this.messageService.findByConversationId(this.conversationId, this.currentPage)
      .subscribe(res => {
        this.messages = res.content.reverse();
        this.totalElementCount = res.totalElements;
      });
  }

  loadMoreMessages() {
    if (this.messages.length >= this.totalElementCount) {
      return;
    }
    this.isLoading = true;

    this.messageService.findByConversationId(this.conversationId, ++this.currentPage)
      .subscribe(res => {
        const temp = this.messages;
        this.messages = res.content.reverse();
        this.messages.push(...temp);
        this.isLoading = false;
      });
  }

  createReplyForm() {
    this.replyForm = this.fb.group({
      message: ['', Validators.required],
      conversationId: [this.conversationId, Validators.required]
    });
  }

  sendReply() {
    if (this.replyForm.invalid) {
      return;
    }

    this.messageService.create(this.replyForm.value)
      .subscribe(res => {
        this.snackBar.open('Wysłano odpowiedź', '', { duration: 3000, panelClass: 'green-snackbar' });

        this.createReplyForm();
        this.messages.push(res);
      }, err => this.snackBar.open('Niepoprawna wiadomość', '', { duration: 3000, panelClass: 'red-snackbar' })
      );
  }
}
