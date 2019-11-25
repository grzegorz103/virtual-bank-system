import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators, NgForm } from '@angular/forms';
import { UserService } from 'src/app/shared/services/user.service';
import { User } from '../../models/user';
import { MatPaginator, MatSort, MatDialog, MatTableDataSource, MatSnackBar } from '@angular/material';
import { EmployeeAddComponent } from '../misc/employee-add/employee-add.component';
import { EmployeeDetailsComponent } from '../misc/employee-details/employee-details.component';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.scss']
})
export class EmployeeListComponent implements OnInit {

  form: FormGroup;
  employeeList = new MatTableDataSource<User>();
  isLoading = true;

  employeeTabColumns = ['id', 'email', 'locked', 'details', 'edit'];

  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;

  @ViewChild(MatSort, { static: true })
  sort: MatSort;

  @ViewChild('formDirective', { static: true }) private formDirective: NgForm;
  currentDate: Date;
  minDate: Date;

  constructor(private fb: FormBuilder,
    public dialog: MatDialog,
    private snackBar: MatSnackBar,
    private userService: UserService) {
    this.currentDate = new Date();
    this.minDate = new Date(1900, 0, 1);
    this.form = this.fb.group({
      password: ['', Validators.required],
      email: ['', [Validators.email, Validators.required]],
      confirmPassword: ['', Validators.required],
      address: this.getAddressGroup()
    });
  }

  ngOnInit() {
    this.fetchEmployees();
  }

  fetchEmployees() {
    this.userService.findByUserType('ROLE_EMPLOYEE', false).subscribe(res => {
      this.isLoading = false;
      this.employeeList.data = res;
      this.employeeList.paginator = this.paginator;
    });
  }

  changeEmployeeStatus(index: string) {
    this.userService.changeStatus(index).subscribe(res => this.fetchEmployees());
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
    if (this.form.invalid) {
      return;
    }

    this.userService.createEmployee(this.form.value).subscribe(res => {
      this.snackBar.open('Dodano nowego pracownika', '', { duration: 3000, panelClass: 'green-snackbar' });
      this.form.reset();
      this.formDirective.resetForm();
      this.fetchEmployees();
    }, err =>
      this.snackBar.open(err.error.messages, '', { duration: 5000, panelClass: 'red-snackbar' })
    );
  }

  openEditDialog(userId: string) {
    let user = this.employeeList.data.find(e => e.id === userId);

    if (user) {
      const dialogRef = this.dialog.open(EmployeeAddComponent, {
        width: window.innerWidth > 768 ? '50%' : '85%',
        data: { id: userId }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.userService.update(userId, result)
            .subscribe(res => {
              this.fetchEmployees();
              this.snackBar.open('Pomyślnie wykonano operację', '', { duration: 3000, panelClass: 'green-snackbar' });
            },
              err => this.snackBar.open(err.error.messages, '', { duration: 5000, panelClass: 'red-snackbar' }))
        }
      });
    }
  }

  openDetailsDialog(userId: string) {
    let user = this.employeeList.data.find(e => e.id === userId);
    if (user) {
      console.log(userId);
      this.dialog.open(EmployeeDetailsComponent, {
        width: window.innerWidth > 768 ? '50%' : '85%',
        data: user
      });
    }
  }

}
