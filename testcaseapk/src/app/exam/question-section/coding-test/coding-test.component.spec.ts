import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CodingTestComponent } from './coding-test.component';

describe('CodingTestComponent', () => {
  let component: CodingTestComponent;
  let fixture: ComponentFixture<CodingTestComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CodingTestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CodingTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
