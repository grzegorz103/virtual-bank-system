import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-investment-list',
  templateUrl: './investment-list.component.html',
  styleUrls: ['./investment-list.component.scss']
})
export class InvestmentListComponent implements OnInit {

  columns = ['id', 'date', 'currentBalance', 'investmentType', 'edit'];
  constructor(private investmentService: InvestmentService) { }

  ngOnInit() {
  }

}
