import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { McqCreateComponent } from './mcq-create.component';

describe('McqCreateComponent', () => {
  let component: McqCreateComponent;
  let fixture: ComponentFixture<McqCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ McqCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(McqCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
