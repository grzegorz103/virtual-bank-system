import { TestBed } from '@angular/core/testing';

import { TransactionTemplateServiceService } from './transaction-template-service.service';

describe('TransactionTemplateServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TransactionTemplateServiceService = TestBed.get(TransactionTemplateServiceService);
    expect(service).toBeTruthy();
  });
});
