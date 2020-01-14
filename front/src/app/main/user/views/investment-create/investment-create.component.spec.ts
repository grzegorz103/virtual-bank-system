import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InvestmentCreateComponent } from './investment-create.component';

describe('InvestmentCreateComponent', () => {
  let component: InvestmentCreateComponent;
  let fixture: ComponentFixture<InvestmentCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InvestmentCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InvestmentCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
