import { TestBed } from '@angular/core/testing';
import { CreditService } from './credit.service';

describe('CreditService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CreditService = TestBed.get(CreditService);
    expect(service).toBeTruthy();
  });
});
