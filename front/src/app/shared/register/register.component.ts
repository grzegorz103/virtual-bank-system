import {Component, OnInit} from '@angular/core';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {UserService} from '../services/user.service';
import {RegisterFormValidator} from './register-form-validator';
import {MatSnackBar} from '@angular/material';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  form: FormGroup;
  currentDate: Date;
  minDate: Date;
  isRequestSent: boolean;

  constructor(private fb: FormBuilder,
              private snackBar: MatSnackBar,
              private userService: UserService,
              private router: Router) {

    this.currentDate = new Date();
    this.minDate = new Date(1900, 0, 1);

    this.form = this.fb.group({
        password: ['', Validators.compose([
          Validators.required,
          RegisterFormValidator.patternValidator(/\d/, {
            hasNumber: true
          }),
          RegisterFormValidator.patternValidator(/[A-Z]/, {
            hasCapitalCase: true
          }),
          RegisterFormValidator.patternValidator(/[a-z]/, {
            hasSmallCase: true
          }),
          RegisterFormValidator.patternValidator(
            /[ !@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/,
            {
              hasSpecialCharacters: true
            }
          ),
          Validators.minLength(8)
        ])],
        email: ['', [Validators.email, Validators.required]],
        confirmPassword: ['', Validators.required],
        address: this.getAddressGroup()
      },
      {
        validator: RegisterFormValidator.passwordMatchValidator
      });
  }

  ngOnInit() {
  }

  getAddressGroup(): FormGroup {
    return this.fb.group({
      city: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(60)]],
      dateOfBirth: ['', Validators.required],
      houseNumber: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(20)]],
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(40)]],
      phoneNumber: ['', [Validators.required, Validators.minLength(7), Validators.maxLength(15)]],
      postCode: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(6)]],
      street: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(60)]],
      surname: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
    });
  }

  sendRegisterForm() {
    this.isRequestSent = true;
    this.userService.create(this.form.value).subscribe(res => {
        this.snackBar.open('Dziękujemy za rejestrację', '', {duration: 3000, panelClass: 'green-snackbar'});
        this.router.navigateByUrl('/login');
        this.isRequestSent = false;
      }
      , err => {
        this.isRequestSent = false;
        this.snackBar.open(err.error.messages, '', {duration: 3000, panelClass: 'red-snackbar'});
      });
  }

}
