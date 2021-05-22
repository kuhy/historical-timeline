import { TestBed } from '@angular/core/testing';

import { ManageStudyGroupUsersService } from './manage-study-group-users.service';

describe('ManageStudyGroupUsersService', () => {
  let service: ManageStudyGroupUsersService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ManageStudyGroupUsersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
