import { Component, OnInit, Injectable } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { SwUpdate } from '@angular/service-worker';


declare const $: any;

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})

export class NavbarComponent implements OnInit {

  isMobileView: boolean;

  constructor(public authService: AuthService) {
    this.isMobileView = window.innerWidth <= 768;
  }

  ngOnInit() {
    window.onresize = () => this.isMobileView = window.innerWidth <= 768;
  } 

}
@Injectable()
export class PwaService {
  promptEvent: any;
  constructor(private swUpdate: SwUpdate) {
    swUpdate.available.subscribe(event => {
      window.location.reload();

    });

    window.addEventListener('beforeinstallprompt', event => {
      this.promptEvent = event;
    });
  }
}