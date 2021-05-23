import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudyGroupUsersComponent } from './study-group-users.component';

describe('ManageStudyGroupUsersComponent', () => {
  let component: StudyGroupUsersComponent;
  let fixture: ComponentFixture<StudyGroupUsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StudyGroupUsersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudyGroupUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
