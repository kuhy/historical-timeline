import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TimelineCommentComponent } from './timeline-comment.component';

describe('TimelineCommentComponent', () => {
  let component: TimelineCommentComponent;
  let fixture: ComponentFixture<TimelineCommentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TimelineCommentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TimelineCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
