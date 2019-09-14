import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionTemplateServiceComponent } from './transaction-template-service.component';

describe('TransactionTemplateServiceComponent', () => {
  let component: TransactionTemplateServiceComponent;
  let fixture: ComponentFixture<TransactionTemplateServiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransactionTemplateServiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransactionTemplateServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
