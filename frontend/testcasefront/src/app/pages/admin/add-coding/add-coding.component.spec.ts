import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCodingComponent } from './add-coding.component';

describe('AddCodingComponent', () => {
  let component: AddCodingComponent;
  let fixture: ComponentFixture<AddCodingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddCodingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCodingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
