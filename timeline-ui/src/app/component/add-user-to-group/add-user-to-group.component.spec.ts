import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUserToGroupComponent } from './add-user-to-group.component';

describe('AddUserToGroupComponent', () => {
  let component: AddUserToGroupComponent;
  let fixture: ComponentFixture<AddUserToGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddUserToGroupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUserToGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
