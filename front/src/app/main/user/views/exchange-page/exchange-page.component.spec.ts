import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExchangePageComponent } from './exchange-page.component';

describe('ExchangePageComponent', () => {
  let component: ExchangePageComponent;
  let fixture: ComponentFixture<ExchangePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExchangePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExchangePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
