import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/shared/services/user.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { User } from '../../models/user';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

  searchForm: FormGroup;
  user: User;

  constructor(private userService: UserService,
    private fb: FormBuilder) { }

  ngOnInit() {
    this.createSearchForm();
  }

  createSearchForm() {
    this.searchForm = this.fb.group({
      identifier: ['']
    });
  }

  searchUser() {
    const identifier = this.searchForm.get('identifier').value;
    if (!identifier) {
      return;
    }

    this.userService.findByIdentifier(identifier)
      .subscribe(res => this.user = res, err => alert('User nie znaleziony'));
  }

  changeUserStatus() {
    this.userService.changeStatus(this.user.id)
      .subscribe(res => this.user = res);
  }
}
