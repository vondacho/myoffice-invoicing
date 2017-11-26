import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HonoraryEditComponent } from './honorary-edit.component';

describe('HonoraryEditComponent', () => {
  let component: HonoraryEditComponent;
  let fixture: ComponentFixture<HonoraryEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HonoraryEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HonoraryEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
