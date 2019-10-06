import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  form: FormGroup;

  constructor(private fb: FormBuilder,
    private userService: UserService) {
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      email: ['', Validators.required],
      confirmPassword: ['', Validators.required],
      address: this.getAddressGroup()
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
    this.userService.create(this.form.value).subscribe(res => alert('Dziekujemy za rejestracje'));
  }

}
