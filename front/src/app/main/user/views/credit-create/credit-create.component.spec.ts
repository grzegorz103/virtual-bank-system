import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreditCreateComponent } from './credit-create.component';

describe('CreditCreateComponent', () => {
  let component: CreditCreateComponent;
  let fixture: ComponentFixture<CreditCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreditCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreditCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
