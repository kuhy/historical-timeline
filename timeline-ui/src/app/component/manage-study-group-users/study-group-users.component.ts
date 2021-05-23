import { Component, OnInit } from '@angular/core';
import {UserDTO} from "../../dto/user-dto";
import {UserService} from "../../service/user.service";
import {StudyGroupService} from "../../service/study-group.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-manage-study-group-users',
  templateUrl: './study-group-users.component.html',
  styleUrls: ['./study-group-users.component.css']
})
export class StudyGroupUsersComponent implements OnInit {

  studyGroupId: number
  isTeacher = false
  users: UserDTO[] = []

  constructor(private router: Router,
              private userService: UserService,
              private studyGroupService: StudyGroupService,
              private activatedRoute: ActivatedRoute) {
    this.studyGroupId = 0

    this.activatedRoute.paramMap.subscribe(params => {
      this.studyGroupId = Number(params.get('studyGroupId'))
    })
  }

  ngOnInit(): void {
    this.getTeacherStatus()
    this.loadUsers()
  }

  getTeacherStatus() {
    this.userService.isTeacher().subscribe(response => {
      this.isTeacher = response;
    })
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe(response => {
      this.users = response.content;
    })
  }

  // ========== Return to study groups ==========
  backToStudyGroups() {
    this.router.navigate([`/groups`]);
  }

  // ========== Add user to study group ==========
  addUserToStudyGroup(userId: number) {
    // TODO not working
    this.studyGroupService.addUserToStudyGroup(this.studyGroupId, userId)
  }

  // ========== Remove user from study group ==========
  removeUserFromStudyGroup(userId: number) {
    // TODO not working
    this.studyGroupService.removeUserFromStudyGroup(this.studyGroupId, userId)
  }
}
