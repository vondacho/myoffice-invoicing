import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HonoraryListComponent } from './honorary-list.component';

describe('HonoraryListComponent', () => {
  let component: HonoraryListComponent;
  let fixture: ComponentFixture<HonoraryListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HonoraryListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HonoraryListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
