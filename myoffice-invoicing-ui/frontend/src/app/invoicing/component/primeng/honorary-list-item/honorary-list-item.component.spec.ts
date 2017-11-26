import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HonoraryListItemComponent } from './honorary-list-item.component';

describe('HonoraryListItemComponent', () => {
  let component: HonoraryListItemComponent;
  let fixture: ComponentFixture<HonoraryListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HonoraryListItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HonoraryListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
