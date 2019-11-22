import { Component, OnInit, ViewChild } from '@angular/core';
import { Conversation } from '../../models/conversation';
import { ConversationService } from '../../services/conversation.service';
import { FormGroup, Validators, FormBuilder, NgForm } from '@angular/forms';
import { MatSnackBar } from '@angular/material';

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

  conversationForm: FormGroup;
  @ViewChild('formDirective', { static: true }) private formDirective: NgForm;

  constructor(private conversationService: ConversationService,
    private snackBar: MatSnackBar,
    private fb: FormBuilder) { }

  ngOnInit() {
    this.fetchUserConversations();
    this.fetchMyConversations();
    this.createConversationForm();
  }

  fetchUserConversations() {
    this.conversationService.findUserEmployeeConversations()
      .subscribe(res => { this.userConversations = res; this.isLoadingUserConversations = false });
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
      conversationDirectionType: ['EMPLOYEE_TO_ADMIN', Validators.required]
    });
  }

  sendConversation() {
    this.conversationService.create(this.conversationForm.value)
      .subscribe(res => {
        this.snackBar.open('Utworzono wpłatę', '', { duration: 3000, panelClass: 'green-snackbar' });
   
        this.fetchMyConversations();
        this.conversationForm.reset();
        this.formDirective.resetForm();
      },err=>{
        this.snackBar.open('Błąd tworzenia zgłoszenia', '', { duration: 3000, panelClass: 'red-snackbar' });
      });
  }

  changeStatus(id: number) {
    this.conversationService.changeStatus(id)
      .subscribe(res => this.fetchUserConversations());
  }

}
