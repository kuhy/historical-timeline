import { TestBed } from '@angular/core/testing';

import { HistoricalEventService } from './historical-event.service';

describe('HistoricalEventService', () => {
  let service: HistoricalEventService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HistoricalEventService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
