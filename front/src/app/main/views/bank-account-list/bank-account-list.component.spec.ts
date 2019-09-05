import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BankAccountListComponent } from './bank-account-list.component';

describe('BankAccountListComponent', () => {
  let component: BankAccountListComponent;
  let fixture: ComponentFixture<BankAccountListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BankAccountListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BankAccountListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
