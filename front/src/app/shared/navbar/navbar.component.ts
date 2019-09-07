import { Component, OnInit } from '@angular/core';


declare const $: any;

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
}) 

export class NavbarComponent implements OnInit {

  isMobileView: boolean;

  constructor() { }

  ngOnInit() {
    window.onresize = () => this.isMobileView = window.innerWidth <= 768;
  }
  
}