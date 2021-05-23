import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudyGroupComponent } from './study-group.component';

describe('StudyGroupsComponent', () => {
  let component: StudyGroupComponent;
  let fixture: ComponentFixture<StudyGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StudyGroupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudyGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
