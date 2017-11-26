import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DebtorListItemComponent } from './debtor-list-item.component';

describe('DebtorListItemComponent', () => {
  let component: DebtorListItemComponent;
  let fixture: ComponentFixture<DebtorListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DebtorListItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DebtorListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
