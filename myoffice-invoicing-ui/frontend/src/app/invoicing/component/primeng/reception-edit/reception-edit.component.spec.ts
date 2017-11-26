import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReceptionEditComponent } from './reception-edit.component';

describe('ReceptionEditComponent', () => {
  let component: ReceptionEditComponent;
  let fixture: ComponentFixture<ReceptionEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReceptionEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReceptionEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
