import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatecodeComponent } from './updatecode.component';

describe('UpdatecodeComponent', () => {
  let component: UpdatecodeComponent;
  let fixture: ComponentFixture<UpdatecodeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdatecodeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdatecodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
