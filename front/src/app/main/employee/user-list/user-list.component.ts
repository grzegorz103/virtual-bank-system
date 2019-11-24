import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from 'src/app/shared/services/user.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { User } from '../../models/user';
import { MatTableDataSource, MatPaginator, MatSort, MatDialog, MatSnackBar } from '@angular/material';
import { UserEditDialogComponent } from '../misc/user-edit-dialog/user-edit-dialog.component';
import { UserDialogComponent } from '../misc/user-dialog/user-dialog.component';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

  searchForm: FormGroup;
  user: User;
  userList = new MatTableDataSource<User>();
  isLoading = true;

  newUsersTabColumns = ['id', 'email', 'details', 'edit', 'activate'];

  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;

  @ViewChild(MatSort, { static: true })
  sort: MatSort;

  constructor(private userService: UserService,
    public dialog: MatDialog,
    private snackBar: MatSnackBar,
    private fb: FormBuilder) { }

  ngOnInit() {
    this.createSearchForm();
    this.fetchNewUsers();
  }

  createSearchForm() {
    this.searchForm = this.fb.group({
      identifier: ['']
    });
  }

  searchUser() {
    const identifier: string = this.searchForm.get('identifier').value;
    if (!identifier) {
      return;
    }
    console.log(identifier);

    this.userService.findByIdentifier(identifier)
      .subscribe(res => this.user = res, err => 
        this.snackBar.open('Klient nie odnaleziony', '', { duration: 3000, panelClass: 'red-snackbar' })
        );
  }

  changeUserStatus() {
    this.userService.changeStatus(this.user.id)
      .subscribe(res => this.user = res);
  }

  fetchNewUsers() {
    this.userService.findByUserType('ROLE_USER', true)
      .subscribe(res => {
        this.userList.data = res;
        this.userList.paginator = this.paginator;
        this.isLoading = false;
      })
  }


  openEditDialog(userId: string) {

    const dialogRef = this.dialog.open(UserEditDialogComponent, {
      width: window.innerWidth > 768 ? '50%' : '85%',
      data: { id: userId }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.userService.update(userId, result)
          .subscribe(res => {
            this.fetchNewUsers();
            if(this.user && userId === this.user.id){
              this.searchUser();
            }
            this.snackBar.open('Udana edycja klienta', '', { duration: 3000, panelClass: 'green-snackbar' })
          }, err =>{
            this.snackBar.open(err.error.messages, '', { duration: 5000, panelClass: 'red-snackbar' })
         
          });
      }
    });

  }

  openDetailsDialog(userId: string) {
    let user = this.userList.data.find(e => e.id === userId);
    if (user) {
      console.log(userId);
      this.dialog.open(UserDialogComponent, {
        width: window.innerWidth > 768 ? '50%' : '85%',
        data: user
      });
    }
  }

  activateUser(userId: string) {
    this.userService.changeActivateStatus(userId)
      .subscribe(res => this.fetchNewUsers());
  }
}
