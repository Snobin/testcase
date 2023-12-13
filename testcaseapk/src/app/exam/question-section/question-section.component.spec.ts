import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionSectionComponent } from './question-section.component';

describe('QuestionSectionComponent', () => {
  let component: QuestionSectionComponent;
  let fixture: ComponentFixture<QuestionSectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuestionSectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuestionSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
