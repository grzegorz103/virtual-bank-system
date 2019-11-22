import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BankAccountDialogComponent } from './bank-account-dialog.component';

describe('BankAccountDialogComponent', () => {
  let component: BankAccountDialogComponent;
  let fixture: ComponentFixture<BankAccountDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BankAccountDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BankAccountDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
