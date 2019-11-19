import { Component, OnInit } from '@angular/core';
import { PwaService } from '../navbar/navbar.component';
import { AuthService } from '../services/auth.service';
@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  constructor(public Pwa: PwaService, public authService: AuthService ) { }

  ngOnInit() {
  }

  installPwa(): void {
    this.Pwa.promptEvent.prompt();
  }

}
