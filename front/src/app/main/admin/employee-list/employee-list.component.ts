import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserService } from 'src/app/shared/services/user.service';
import { User } from '../../models/user';
import { MatPaginator, MatSort, MatDialog } from '@angular/material';
import { EmployeeAddComponent } from '../misc/employee-add/employee-add.component';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.scss']
})
export class EmployeeListComponent implements OnInit {

  form: FormGroup;
  employeeList: User[];
  isLoading = true;

  employeeTabColumns = ['id', 'email', 'locked', 'details', 'edit'];

  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;

  @ViewChild(MatSort, { static: true })
  sort: MatSort;

  constructor(private fb: FormBuilder,
    public dialog: MatDialog,
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

  openEditDialog(userId: string) {
    const dialogRef = this.dialog.open(EmployeeAddComponent, {
      width: window.innerWidth > 768 ? '50%' : '85%',
      data: { id: userId }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.userService.update(userId, result)
          .subscribe(res => this.fetchEmployees());
      }
    });
  }
}
