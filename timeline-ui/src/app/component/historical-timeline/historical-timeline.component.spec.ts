import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoricalTimelineComponent } from './historical-timeline.component';

describe('HistoricalTimelineComponent', () => {
  let component: HistoricalTimelineComponent;
  let fixture: ComponentFixture<HistoricalTimelineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HistoricalTimelineComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HistoricalTimelineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
