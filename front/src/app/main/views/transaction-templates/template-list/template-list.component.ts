import { Component, OnInit } from '@angular/core';
import { TransactionTemplate } from 'src/app/main/models/transaction-template';
import { TransactionTemplateService } from 'src/app/main/services/transaction-template-service.service';

@Component({
  selector: 'app-template-list',
  templateUrl: './template-list.component.html',
  styleUrls: ['./template-list.component.scss']
})
export class TemplateListComponent implements OnInit {

  templates: TransactionTemplate[];

  constructor(private transactionTemplateService: TransactionTemplateService) { }

  ngOnInit() {
    this.fetchData();
  }

  fetchData() {
    this.transactionTemplateService.findAll().subscribe(res => this.templates = res);
  }
}
