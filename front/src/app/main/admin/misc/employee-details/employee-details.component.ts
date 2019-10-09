import { Component, OnInit, Inject } from '@angular/core';
import { User } from 'src/app/main/models/user';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { EmployeeAddComponent } from '../employee-add/employee-add.component';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-employee-details',
  templateUrl: './employee-details.component.html',
  styleUrls: ['./employee-details.component.scss']
})
export class EmployeeDetailsComponent implements OnInit {

  user: User;

  constructor(
    public dialogRef: MatDialogRef<EmployeeAddComponent>,
    @Inject(MAT_DIALOG_DATA) public data: User) {

    if (data) {
      this.user = data;
    }
  }


  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
  }
}
