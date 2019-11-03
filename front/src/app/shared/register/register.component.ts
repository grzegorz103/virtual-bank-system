import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../services/user.service';
import { RegisterFormValidator } from './register-form-validator';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  form: FormGroup;

  constructor(private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private userService: UserService) {
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
      email: ['', Validators.required],
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
      city: ['', Validators.required],
      dateOfBirth: ['', Validators.required],
      houseNumber: ['', Validators.required],
      name: ['', Validators.required],
      phoneNumber: ['', Validators.required],
      postCode: ['', Validators.required],
      street: ['', Validators.required],
      surname: ['', Validators.required],
    });
  }

  sendRegisterForm() {
    this.userService.create(this.form.value).subscribe(res =>
      this.snackBar.open('Dziękujemy za rejestrację', '', { duration: 3000, panelClass: 'green-snackbar' })
      , err => {
        this.snackBar.open(err.error.messages, '', { duration: 3000, panelClass: 'red-snackbar' });
      });
  }

}
