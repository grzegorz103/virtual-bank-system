import { TestBed } from '@angular/core/testing';

import { CurrencyTypeService } from './currency-type.service';

describe('CurrencyTypeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CurrencyTypeService = TestBed.get(CurrencyTypeService);
    expect(service).toBeTruthy();
  });
});
