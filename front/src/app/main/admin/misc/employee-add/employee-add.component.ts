import { Component, OnInit, Inject } from '@angular/core';
import { User } from 'src/app/main/models/user';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { UserService } from 'src/app/shared/services/user.service';


export interface EmployeeAddDto {
  id: string;
}

@Component({
  selector: 'app-employee-add',
  templateUrl: './employee-add.component.html',
  styleUrls: ['./employee-add.component.scss']
})
export class EmployeeAddComponent implements OnInit {

  user: User;

  constructor(
    public dialogRef: MatDialogRef<EmployeeAddComponent>,
    @Inject(MAT_DIALOG_DATA) public data: EmployeeAddDto,
    private userService: UserService) {

    if (data.id) {
      this.userService.findById(data.id)
        .subscribe(res => this.user = res);
    };
  }


  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
  }

}