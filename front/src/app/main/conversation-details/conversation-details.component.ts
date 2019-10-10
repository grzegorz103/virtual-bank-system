import { Component, OnInit, ViewChild } from '@angular/core';
import { Message } from '../models/message';
import { ActivatedRoute } from '@angular/router';
import { MessageService } from '../services/message.service';
import { FormBuilder, FormGroup, Validators, NgForm } from '@angular/forms';
import { AuthService } from 'src/app/shared/services/auth.service';

@Component({
  selector: 'app-conversation-details',
  templateUrl: './conversation-details.component.html',
  styleUrls: ['./conversation-details.component.scss']
})
export class ConversationDetailsComponent implements OnInit {

  messages: Message[];
  conversationId: string;
  replyForm: FormGroup;

  @ViewChild('formDirective', { static: true }) private formDirective: NgForm;

  constructor(private route: ActivatedRoute,
    private fb: FormBuilder,
    private authService: AuthService,
    private messageService: MessageService) {
    this.conversationId = this.route.snapshot.paramMap.get('id');
  }

  ngOnInit() {
    this.fetchMessages();
    this.createReplyForm();
  }

  fetchMessages() {
    this.messageService.findByConversationId(this.conversationId)
      .subscribe(res => this.messages = res);
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
    };
    this.messageService.create(this.replyForm.value)
      .subscribe(res => {
        alert('Dodano odpowiedź');
        this.replyForm.reset();
        this.formDirective.resetForm();
        this.fetchMessages();
      });
  }
}
