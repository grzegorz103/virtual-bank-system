import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from 'src/app/shared/services/user.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { User } from '../../models/user';
import { MatTableDataSource, MatPaginator, MatSort, MatDialog } from '@angular/material';
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

  newUsersTabColumns = ['id', 'email', 'locked', 'details', 'edit'];

  @ViewChild(MatPaginator, { static: true })
  paginator: MatPaginator;

  @ViewChild(MatSort, { static: true })
  sort: MatSort;

  constructor(private userService: UserService,
    public dialog: MatDialog,
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
    const identifier = this.searchForm.get('identifier').value;
    if (!identifier) {
      return;
    }

    this.userService.findByIdentifier(identifier)
      .subscribe(res => this.user = res, err => alert('User nie znaleziony'));
  }

  changeUserStatus() {
    this.userService.changeStatus(this.user.id)
      .subscribe(res => this.user = res);
  }

  fetchNewUsers() {
    this.userService.findByUserType('ROLE_USER', true)
      .subscribe(res => {
        this.userList.data.push(...res);
        this.userList.paginator = this.paginator;
        this.isLoading = false;
      })
  }

  
  openEditDialog(userId: string) {
    let user = this.userList.data.find(e => e.id === userId);

    if (user) {
      const dialogRef = this.dialog.open(UserEditDialogComponent, {
        width: window.innerWidth > 768 ? '50%' : '85%',
        data: { id: userId }
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          this.userService.update(userId, result)
            .subscribe(res => this.fetchNewUsers());
        }
      });
    }
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
}
