import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplateDetailsComponent } from './template-details.component';

describe('TemplateDetailsComponent', () => {
  let component: TemplateDetailsComponent;
  let fixture: ComponentFixture<TemplateDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TemplateDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TemplateDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
