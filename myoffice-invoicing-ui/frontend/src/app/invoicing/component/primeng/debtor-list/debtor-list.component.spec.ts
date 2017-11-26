import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DebtorListComponent } from './debtor-list.component';

describe('DebtorListComponent', () => {
  let component: DebtorListComponent;
  let fixture: ComponentFixture<DebtorListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DebtorListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DebtorListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
