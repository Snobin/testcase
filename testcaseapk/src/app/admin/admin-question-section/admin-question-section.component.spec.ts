import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminQuestionSectionComponent } from './admin-question-section.component';

describe('AdminQuestionSectionComponent', () => {
  let component: AdminQuestionSectionComponent;
  let fixture: ComponentFixture<AdminQuestionSectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminQuestionSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminQuestionSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
