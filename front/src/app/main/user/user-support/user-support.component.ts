import { Component, OnInit, ViewChild } from '@angular/core';
import { Conversation } from '../../models/conversation';
import { FormGroup, NgForm, FormBuilder, Validators } from '@angular/forms';
import { ConversationService } from '../../services/conversation.service';
import { MatTableDataSource, MatPaginator, MatSnackBar } from '@angular/material';
import {faQuestionCircle} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-user-support',
  templateUrl: './user-support.component.html',
  styleUrls: ['./user-support.component.scss']
})
export class UserSupportComponent implements OnInit {

  isLoadingMyConversations = true;

  myConversationColumns = ['topic', 'creationDate', 'status', 'details']
  myConversations = new MatTableDataSource<Conversation>();

  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;

  conversationForm: FormGroup;
  @ViewChild('formDirective', { static: true }) private formDirective: NgForm;

  faQuestionCircle = faQuestionCircle;

  constructor(private conversationService: ConversationService,
    private snackBar: MatSnackBar,
    private fb: FormBuilder) { }

  ngOnInit() {
    this.fetchMyConversations();
    this.createConversationForm();
  }

  fetchMyConversations() {
    this.conversationService.findByUser()
      .subscribe(res => {
        this.myConversations.data = res;
        this.isLoadingMyConversations = false;
        this.myConversations.paginator = this.paginator; });
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
        this.snackBar.open('Utworzono zgłoszenie', '', { duration: 3000, panelClass: 'green-snackbar' })
        this.fetchMyConversations();
        this.conversationForm.reset();
        this.formDirective.resetForm();
      });
  }

}
