import { TestBed } from '@angular/core/testing';

import { ExchangeCurrencyService } from '../services/exchange-currency.service';

describe('ExchangeCurrencyService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ExchangeCurrencyService = TestBed.get(ExchangeCurrencyService);
    expect(service).toBeTruthy();
  });
});
