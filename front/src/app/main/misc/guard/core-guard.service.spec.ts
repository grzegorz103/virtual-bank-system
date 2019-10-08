import { TestBed } from '@angular/core/testing';
import { CoreGuardService } from './core-guard.service';


describe('CoreGuardService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CoreGuardService = TestBed.get(CoreGuardService);
    expect(service).toBeTruthy();
  });
});
