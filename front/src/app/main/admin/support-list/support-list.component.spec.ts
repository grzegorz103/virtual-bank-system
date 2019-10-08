import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SupportListComponent } from './support-list.component';

describe('SupportListComponent', () => {
  let component: SupportListComponent;
  let fixture: ComponentFixture<SupportListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SupportListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupportListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
