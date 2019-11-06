import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrencyTypeEditComponent } from './currency-type-edit.component';

describe('CurrencyTypeEditComponent', () => {
  let component: CurrencyTypeEditComponent;
  let fixture: ComponentFixture<CurrencyTypeEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CurrencyTypeEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CurrencyTypeEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
