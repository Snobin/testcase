import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CodingquestionComponent } from './codingquestion.component';

describe('CodingquestionComponent', () => {
  let component: CodingquestionComponent;
  let fixture: ComponentFixture<CodingquestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CodingquestionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CodingquestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
