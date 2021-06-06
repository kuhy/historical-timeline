import {Component, OnInit} from '@angular/core';
import {StudyGroupService} from "../../service/study-group.service";
import {StudyGroupDTO} from "../../dto/study-group-dto";
import {UserService} from "../../service/user.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {StudyGroupCreateDTO} from "../../dto/study-group-create-dto";
import {faEdit, faEye, faTrash, faUserEdit} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-study-groups',
  templateUrl: './study-group.component.html',
  styleUrls: ['./study-group.component.css']
})
export class StudyGroupComponent implements OnInit {
  faTrash = faTrash;
  faEye = faEye
  faEdit = faEdit
  faUserEdit = faUserEdit

  studyGroups: StudyGroupDTO[] = [];
  isTeacher = false;

  showUpdateGroupModal = false;
  showCreateGroupModal = false;

  updateGroupDTO: StudyGroupDTO;

  updateForm: FormGroup;
  createForm: FormGroup;

  loading = false;
  submitted = false;

  constructor(private formBuilder: FormBuilder,
              private studyGroupService: StudyGroupService,
              private userService: UserService,
              private router: Router) {
    this.updateGroupDTO = new StudyGroupDTO()
    this.updateForm = this.formBuilder.group({
      name: ['', Validators.required],
    });
    this.createForm = this.formBuilder.group({
      name: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.getTeacherStatus()
    this.loadStudyGroups();
  }

  private getTeacherStatus() {
    this.userService.isTeacher().subscribe(response => {
      this.isTeacher = response;
    });
  }

  private loadStudyGroups() {
    this.studyGroupService.getAllGroups().subscribe(response => {
      this.studyGroups = response.content;
      this.studyGroups = this.studyGroups.sort(function (a, b) {
        return ('' + a.name).localeCompare(b.name);})
    })
  }

  deleteGroup(id: number) {
    this.studyGroupService.deleteGroup(id).subscribe(response => {
      this.loadStudyGroups()
    });
  }


  // ========== Update ==========
  get fu() { return this.updateForm.controls; }

  updateGroupModal(group: StudyGroupDTO) {
    this.closeCreateGroupModal()
    this.updateGroupDTO.id = group.id;
    this.updateGroupDTO.users = group.users;
    this.updateGroupDTO.historicalTimelines = group.historicalTimelines;

    this.showUpdateGroupModal = true;
    this.submitted = false;
    this.updateForm.setValue({name: this.studyGroups.find(x => x.id == group.id)!.name})
  }

  updateGroup() {
    this.submitted = true

    if (this.updateForm.invalid) {
      return;
    }

    this.loading = true;
    this.updateGroupDTO.name = this.fu.name.value;
    this.studyGroupService.updateGroup(this.updateGroupDTO).subscribe(response => {
        this.loadStudyGroups();
    });
    this.loading = false;

    this.closeUpdateGroupModal();
  }

  closeUpdateGroupModal() {
    this.updateGroupDTO = new StudyGroupDTO();
    this.showUpdateGroupModal = false;
  }

  // ========== Create ==========

  get fc() { return this.createForm.controls; }

  createGroupModal() {
    this.closeUpdateGroupModal();
    this.showCreateGroupModal = true;
    this.submitted = false;
  }

  createGroup() {
    this.submitted = true

    if (this.createForm.invalid) {
      return;
    }

    this.loading = true;
    let group = new StudyGroupCreateDTO();
    group.name = this.fc.name.value;
    this.studyGroupService.createGroup(group).subscribe(response => {
      this.loadStudyGroups();
    });
    this.loading = false;

    this.closeCreateGroupModal();
  }

  closeCreateGroupModal() {
    this.showCreateGroupModal = false;
  }

  // ========== Show timelines ==========

  showTimelines(studyGroupId: number) {
    this.router.navigate([`/groups/${studyGroupId}`]);
  }

  // ========== Add user ==========

  manageUsers(studyGroupId: number) {
    this.router.navigate([`/groups/manage_users/${studyGroupId}`])
  }
  logout() {
    this.userService.logout().subscribe(data => {
      this.router.navigate(['/login']);
    });
  }
  backToStudyGroups() {
    this.router.navigate([`/groups`]);
  }
  backToHistoricalTimelines(){
    this.router.navigate([`/timelines`]);
  }
  backToHistoricalEvents(){
    this.router.navigate([`/events`]);
  }
}
