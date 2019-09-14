import { TestBed } from '@angular/core/testing';
import { TransactionTemplateService } from './transaction-template-service.service';


describe('TransactionTemplateServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TransactionTemplateService = TestBed.get(TransactionTemplateService);
    expect(service).toBeTruthy();
  });
});
