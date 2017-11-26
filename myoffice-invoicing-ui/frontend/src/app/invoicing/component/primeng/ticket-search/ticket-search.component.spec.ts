import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TicketSearchComponent } from './ticket-search.component';

describe('TicketSearchComponent', () => {
  let component: TicketSearchComponent;
  let fixture: ComponentFixture<TicketSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TicketSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TicketSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
