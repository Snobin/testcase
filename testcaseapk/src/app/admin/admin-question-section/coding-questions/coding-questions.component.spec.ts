import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CodingQuestionsComponent } from './coding-questions.component';

describe('CodingQuestionsComponent', () => {
  let component: CodingQuestionsComponent;
  let fixture: ComponentFixture<CodingQuestionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CodingQuestionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CodingQuestionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
