import { Component, OnInit, ViewChild } from '@angular/core';
import { Conversation } from '../models/conversation';
import { FormGroup, NgForm, FormBuilder, Validators } from '@angular/forms';
import { ConversationService } from '../services/conversation.service';

@Component({
  selector: 'app-user-support',
  templateUrl: './user-support.component.html',
  styleUrls: ['./user-support.component.scss']
})
export class UserSupportComponent implements OnInit {

  isLoadingMyConversations = true;

  myConversationColumns = ['topic', 'creationDate', 'status', 'details']
  myConversations: Conversation[];

  conversationForm: FormGroup;
  @ViewChild('formDirective', { static: true }) private formDirective: NgForm;
  
  constructor(private conversationService: ConversationService,
    private fb: FormBuilder) { }

  ngOnInit() {
    this.fetchMyConversations();
    this.createConversationForm();
  }

  fetchMyConversations() {
    this.conversationService.findByUser()
      .subscribe(res => { this.myConversations = res; this.isLoadingMyConversations = false });
  }

  createConversationForm() {
    this.conversationForm = this.fb.group({
      topic: ['', Validators.required],
      description: ['', Validators.required],
      conversationType: ['ACTIVE', Validators.required],
      conversationDirectionType: ['USER_TO_EMPLOYEE', Validators.required]
    });
  }

  sendConversation() {
    this.conversationService.create(this.conversationForm.value)
      .subscribe(res =>{
      alert('Wysłano zgłoszenie');
        this.fetchMyConversations();
        this.conversationForm.reset();
        this.formDirective.resetForm();
      });
  }

}