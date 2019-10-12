import { Component, OnInit, Inject } from '@angular/core';
import { User } from 'src/app/main/models/user';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { UserService } from 'src/app/shared/services/user.service';


export interface UserModalDTO {
  id: string;
}

@Component({
  selector: 'app-user-edit-dialog',
  templateUrl: './user-edit-dialog.component.html',
  styleUrls: ['./user-edit-dialog.component.scss']
})


export class UserEditDialogComponent implements OnInit {

  user: User;

  constructor(
    public dialogRef: MatDialogRef<UserEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: UserModalDTO,
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
