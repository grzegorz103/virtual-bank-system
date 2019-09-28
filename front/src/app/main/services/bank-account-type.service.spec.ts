import { TestBed } from '@angular/core/testing';

import { BankAccountTypeService } from './bank-account-type.service';

describe('BankAccountTypeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BankAccountTypeService = TestBed.get(BankAccountTypeService);
    expect(service).toBeTruthy();
  });
});
