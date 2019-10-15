import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreditListComponent } from './credit-list.component';

describe('CreditListComponent', () => {
  let component: CreditListComponent;
  let fixture: ComponentFixture<CreditListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreditListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreditListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
