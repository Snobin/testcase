import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCodingComponent } from './view-coding.component';

describe('ViewCodingComponent', () => {
  let component: ViewCodingComponent;
  let fixture: ComponentFixture<ViewCodingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewCodingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewCodingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
