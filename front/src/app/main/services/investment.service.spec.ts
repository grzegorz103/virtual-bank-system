import { TestBed } from '@angular/core/testing';

import { InvestmentService } from './investment.service';

describe('InvestmentService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: InvestmentService = TestBed.get(InvestmentService);
    expect(service).toBeTruthy();
  });
});
