import { Component, OnInit } from '@angular/core';


declare const $: any;

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
}) 

export class NavbarComponent implements OnInit {

  mobile: boolean;

  constructor() { }

  ngOnInit() {

  }
  isMobileView() {
    if ($(window).width() > 991) {
      return false;
    }
    return true;
  };
}
