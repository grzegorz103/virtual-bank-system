import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserService } from 'src/app/shared/services/user.service';
import { User } from '../../models/user';
import { MatPaginator, MatSort } from '@angular/material';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.scss']
})
export class EmployeeListComponent implements OnInit {

  form: FormGroup;
  employeeList: User[];
  isLoading = true;

  employeeTabColumns = ['id', 'email', 'locked', 'details'];

  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;

  @ViewChild(MatSort, { static: true })
  sort: MatSort;

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
    this.fetchEmployees();
  }

  fetchEmployees() {
    this.userService.findByUserType('ROLE_ADMIN').subscribe(res => {
      this.isLoading = false;
      this.employeeList = res;
    });
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
