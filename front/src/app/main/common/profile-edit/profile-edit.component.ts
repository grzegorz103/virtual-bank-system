import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/shared/services/user.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from 'src/app/shared/services/auth.service';
import { User } from '../../models/user';

@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrls: ['./profile-edit.component.scss']
})
export class ProfileEditComponent implements OnInit {

  user: User;
  passwordForm: FormGroup;

  constructor(private userService: UserService,
    public authService: AuthService,
    private fb: FormBuilder) { }

  ngOnInit() {
    this.createPasswordForm();
    this.fetchUserDetails();
  }

  createPasswordForm() {
    this.passwordForm = this.fb.group({
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    });
  }

  sendPasswordEditForm() {
    this.userService.editPassword(this.passwordForm.value)
    .subscribe(res=>alert('Hasło zmienione'));
    
  }

  fetchUserDetails() {
    this.userService.findCurrentUser()
      .subscribe(res => this.user = res);
  }
}
