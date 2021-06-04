import { Component, OnInit } from '@angular/core';
import {UserDTO} from "../../dto/user-dto";
import {UserService} from "../../service/user.service";
import {StudyGroupService} from "../../service/study-group.service";
import {ActivatedRoute, Router} from "@angular/router";
import {faUserMinus, faUserPlus} from '@fortawesome/free-solid-svg-icons';
import {StudyGroupDTO} from "../../dto/study-group-dto";

@Component({
  selector: 'app-manage-study-group-users',
  templateUrl: './study-group-users.component.html',
  styleUrls: ['./study-group-users.component.css']
})
export class StudyGroupUsersComponent implements OnInit {
  faUserPlus = faUserPlus
  faUserMinus = faUserMinus

  studyGroupId: number
  studyGroup: StudyGroupDTO
  isTeacher = false
  allUsers: UserDTO[] = []

  constructor(private router: Router,
              private userService: UserService,
              private studyGroupService: StudyGroupService,
              private activatedRoute: ActivatedRoute) {
    this.studyGroup = new StudyGroupDTO()
    this.studyGroupId = 0

    this.activatedRoute.paramMap.subscribe(params => {
      this.studyGroupId = Number(params.get('studyGroupId'))
    })
  }

  ngOnInit(): void {
    this.getTeacherStatus()
    this.loadUsers()
    this.loadStudyGroup()
  }

  getTeacherStatus() {
    this.userService.isTeacher().subscribe(response => {
      this.isTeacher = response;
    })
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe(response => {
      this.allUsers = response.content;
    })
  }

  loadStudyGroup() {
    this.studyGroupService.getGroup(this.studyGroupId).subscribe(response => {
      this.studyGroup = response;
    })
  }

  get users() {
    if (this.studyGroup && this.studyGroup.users) {
      return this.allUsers.map(user => ({...user,
        isInGroup: this.studyGroup.users.some(groupUser => groupUser.id == user.id)
      }))
    } else {
      return [];
    }
  }

  // ========== Return to study groups ==========
  backToStudyGroups() {
    this.router.navigate([`/groups`]);
  }

  // ========== Add user to study group ==========
  addUserToStudyGroup(userId: number) {
    this.studyGroupService.addUserToStudyGroup(this.studyGroupId, userId).subscribe(response =>
      this.loadStudyGroup()
    )
  }

  // ========== Remove user from study group ==========
  removeUserFromStudyGroup(userId: number) {
    this.studyGroupService.removeUserFromStudyGroup(this.studyGroupId, userId).subscribe(response =>
      this.loadStudyGroup()
    )
  }

  logout() {
    this.userService.logout().subscribe(data => {
      this.router.navigate(['/login']);
    });
  }
}
