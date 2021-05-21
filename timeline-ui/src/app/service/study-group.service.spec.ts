import { TestBed } from '@angular/core/testing';

import { StudyGroupService } from './study-group.service';

describe('StudyGroupService', () => {
  let service: StudyGroupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StudyGroupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
