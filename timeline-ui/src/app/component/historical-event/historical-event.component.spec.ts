import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoricalEventComponent } from './historical-event.component';

describe('HistoricalEventComponent', () => {
  let component: HistoricalEventComponent;
  let fixture: ComponentFixture<HistoricalEventComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HistoricalEventComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoricalEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
