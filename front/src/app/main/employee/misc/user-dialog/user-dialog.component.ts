import { Component, OnInit, Inject } from '@angular/core';
import { User } from 'src/app/main/models/user';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-user-dialog',
  templateUrl: './user-dialog.component.html',
  styleUrls: ['./user-dialog.component.scss']
})

export class UserDialogComponent implements OnInit {

  user: User;

  constructor(
    public dialogRef: MatDialogRef<UserDialogComponent>,
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
