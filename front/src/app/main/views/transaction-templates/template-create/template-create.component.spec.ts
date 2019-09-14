import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplateCreateComponent } from './template-create.component';

describe('TemplateCreateComponent', () => {
  let component: TemplateCreateComponent;
  let fixture: ComponentFixture<TemplateCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TemplateCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TemplateCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
