import { Component, OnInit } from '@angular/core';
import { TransactionTemplate } from 'src/app/main/models/transaction-template';
import { TransactionTemplateService } from 'src/app/main/services/transaction-template-service.service';
import { MatDialog } from '@angular/material';
import { DialogWindowComponent } from 'src/app/main/misc/dialog-window/dialog-window.component';

@Component({
  selector: 'app-template-list',
  templateUrl: './template-list.component.html',
  styleUrls: ['./template-list.component.scss']
})
export class TemplateListComponent implements OnInit {

  templates: TransactionTemplate[];

  // do dodawania/edycji
  template: TransactionTemplate;
  constructor(private transactionTemplateService: TransactionTemplateService,
    public dialog: MatDialog) { }

  ngOnInit() {
    this.fetchData();
  }

  fetchData() {
    this.transactionTemplateService.findAll().subscribe(res => {
    this.templates = res;
      this.templates.sort((o1, o2) => o1.id - o2.id);
    });
  }

  openDialog(index?: any) {
    let isCreating: boolean;
    if (index) {
      isCreating = false;
      this.template = this.templates.find(e => e.id === index);
    } else {
      isCreating = true;
      this.template = new TransactionTemplate();
    }
    const dialogRef = this.dialog.open(DialogWindowComponent, {
      width: '50%',
      data: this.template
    });

    dialogRef.afterClosed().subscribe(result => {
      if (isCreating) {
        alert('kreat');
        this.transactionTemplateService.create(this.template).subscribe(res => this.fetchData());
      } else {
        alert('edit');
        //this.transactionTemplateService.update(this.template.id, this.template);
      }
    });
  }
}
