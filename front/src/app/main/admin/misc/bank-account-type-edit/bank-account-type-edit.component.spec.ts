import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BankAccountTypeEditComponent } from './bank-account-type-edit.component';

describe('BankAccountTypeEditComponent', () => {
  let component: BankAccountTypeEditComponent;
  let fixture: ComponentFixture<BankAccountTypeEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BankAccountTypeEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BankAccountTypeEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
