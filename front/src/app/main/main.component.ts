import { Component, OnInit } from '@angular/core';
import { faMoneyBillWave, faHands } from '@fortawesome/free-solid-svg-icons';
import { faUniversity } from '@fortawesome/free-solid-svg-icons';
import { faHandshake } from '@fortawesome/free-solid-svg-icons';
import { faHandHolding } from '@fortawesome/free-solid-svg-icons';
import { AuthService } from '../shared/services/auth.service';
import { faAddressCard } from '@fortawesome/free-solid-svg-icons';
import { faBars } from '@fortawesome/free-solid-svg-icons';
import { faQuestionCircle } from '@fortawesome/free-solid-svg-icons';
import { faChartBar } from '@fortawesome/free-solid-svg-icons';
import { faUsers } from '@fortawesome/free-solid-svg-icons';
import { faCaretSquareUp } from '@fortawesome/free-solid-svg-icons';
import { faMoneyBill } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
  title = 'front';
  faMoneyBillWave = faMoneyBillWave;
  faUniversity = faUniversity;
  faHandshake = faHandshake;
  faHandHolding = faHandHolding;
  faAddressCard = faAddressCard;
  faBars = faBars;
  faQuestionCircle = faQuestionCircle;
  faChartBar = faChartBar;
  faUsers = faUsers;
  faCaretSquareUp = faCaretSquareUp;
  faMoneyBill = faMoneyBill;

  constructor(public authService: AuthService) { console.log(authService.getUserRoles() + "asd") }

  ngOnInit() {
  }

}
