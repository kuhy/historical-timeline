import { TestBed } from '@angular/core/testing';

import { TimelineCommentService } from './timeline-comment.service';

describe('TimelineCommentService', () => {
  let service: TimelineCommentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TimelineCommentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
