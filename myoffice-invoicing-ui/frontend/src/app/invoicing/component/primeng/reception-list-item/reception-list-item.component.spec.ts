import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReceptionListItemComponent } from './reception-list-item.component';

describe('ReceptionListItemComponent', () => {
  let component: ReceptionListItemComponent;
  let fixture: ComponentFixture<ReceptionListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReceptionListItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReceptionListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
