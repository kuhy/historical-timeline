import { TestBed } from '@angular/core/testing';

import { HistoricalTimelineService } from './historical-timeline.service';

describe('HistoricalTimelineService', () => {
  let service: HistoricalTimelineService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HistoricalTimelineService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
