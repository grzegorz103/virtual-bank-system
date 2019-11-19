import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../services/user.service';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  form: FormGroup;

  constructor(private fb: FormBuilder,
    private userService: UserService,
    private snackBar: MatSnackBar,
    private authService: AuthService,
    private router: Router) {
    this.form = this.fb.group({
      username: ['', [Validators.maxLength(20), Validators.required]],
      password: ['', [Validators.maxLength(120), Validators.required]]
    });
    this.authService.clearLocalStorage();
    this.authService.clearSessionStorage();
  }

  ngOnInit() {
  }

  login() {
    this.userService.login(this.form.get('username').value, this.form.get('password').value)
      .subscribe(res => {
        if (res) {
          this.authService.saveToken(res.headers.get('Authorization'));
          this.authService.setUserRoles();
          this.authService.setUserIdentifier();
          this.snackBar.open('Zalogowano', '', { duration: 3000, panelClass: 'green-snackbar' });
          if (this.authService.hasAdminRole()) {
            this.router.navigateByUrl('/core/admin/stat');
          }
          else if (this.authService.hasEmployeeRole()) {
            this.router.navigateByUrl('/core/employee/payment/create');
          } else {
            this.router.navigateByUrl('/core/bankAccounts');
          }
        }
      }, err => this.snackBar.open('Niepoprawny identyfikator lub hasło', '', { duration: 3000, panelClass: 'red-snackbar' })
      );
  }

}
