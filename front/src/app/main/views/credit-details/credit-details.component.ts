import { Component, OnInit } from '@angular/core';
import { Credit } from '../../models/credit';
import { CreditService } from '../../services/credit.service';
import { ActivatedRoute } from '@angular/router';
import { InstallmentService } from 'src/app/main/services/installment.service';
import { Installment } from '../../models/installment';

@Component({
  selector: 'app-credit-details',
  templateUrl: './credit-details.component.html',
  styleUrls: ['./credit-details.component.scss']
})
export class CreditDetailsComponent implements OnInit {

  credit: Credit;
  installments: Installment[];
  creditId: any;

  constructor(private creditService: CreditService,
    private installmentService: InstallmentService,
    private route: ActivatedRoute) {
    this.creditId = this.route.snapshot.paramMap.get('id');
  }

  ngOnInit() {
    this.fetchCredit();
    this.fetchInstallmentList();
  }

  fetchCredit() {
    this.creditService.findById(this.creditId)
      .subscribe(res => this.credit = res);
  }

  fetchInstallmentList() {
    this.installmentService.findAllByCreditId(this.creditId)
      .subscribe(res => this.installments = res);
  }

}
