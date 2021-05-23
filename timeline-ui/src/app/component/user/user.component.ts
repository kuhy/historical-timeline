import { Component, OnInit } from '@angular/core';
import {UserDTO} from "../../dto/user-dto";
import {UserService} from "../../service/user.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserUpdateDto} from "../../dto/user-update-dto";
import { faEdit, faTrash } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  faEdit = faEdit
  faTrash = faTrash

  isTeacher:boolean = false;
  users: UserDTO[] = [];
  tmp : any;

  showUpdateUserModal = false;
  showCreateUserModal = false;

  updateUserDTO: UserUpdateDto;

  updateForm: FormGroup;
  createForm: FormGroup;


  loading = false;
  submitted = false;

  constructor(private formBuilder: FormBuilder, private userService: UserService) {
    this.updateUserDTO = new UserUpdateDto()
    this.updateForm = this.formBuilder.group({
      username: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      isTeacher: ['', Validators.required],
      hashedPassword: ['', Validators.required]

    });
    this.createForm = this.formBuilder.group({
      username: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      isTeacher: ['', Validators.required],
      password: ['', Validators.required],

    });
  }

  ngOnInit(): void {
    this.getTeacherStatus();
    this.loadUsers();
  }
  getTeacherStatus(){
    this.userService.isTeacher().subscribe(response => {
      this.isTeacher = response;
      console.log(this.isTeacher);
    })
  }

  loadUsers(){
    this.userService.getAllUsers().subscribe(response => {
      this.tmp = response;
      this.users = this.tmp.content;
    })
  }

  deleteUser(id : number){
    this.userService.deleteUser(id).subscribe(response => {
      this.loadUsers();
    })
  }

  // ========== Update ==========
  get fu() { return this.updateForm.controls; }

  updateUserModal(id: number) {
    this.closeCreateUserModal()
    this.updateUserDTO.id = id;
    this.showUpdateUserModal = true;
    this.submitted = false;
  }

  updateUser() {
    this.submitted = true

    if (this.updateForm.invalid) {
      return;
    }

    this.loading = true;
    this.updateUserDTO.username = this.fu.username.value;
    this.updateUserDTO.firstName = this.fu.firstName.value;
    this.updateUserDTO.lastName = this.fu.lastName.value;
    this.updateUserDTO.isTeacher = this.fu.isTeacher.value;
    this.updateUserDTO.hashedPassword = this.fu.hashedPassword.value;


    this.userService.updateUser(this.updateUserDTO).subscribe(response => {
      this.loadUsers();
    });
    this.loading = false;

    this.closeUpdateUserModal();
  }

  closeUpdateUserModal() {
    this.updateUserDTO = new UserUpdateDto();
    this.showUpdateUserModal = false;
  }

  // ========== Create ==========
  get fc() { return this.createForm.controls; }

  createUserModal() {
    this.closeUpdateUserModal();
    this.showCreateUserModal = true;
    this.submitted = false;
  }

  createUser() {
    this.submitted = true

    if (this.createForm.invalid) {
      return;
    }

    this.loading = true;
    let user = new UserDTO();
    user.username = this.fc.username.value;
    user.firstName = this.fc.firstName.value;
    user.lastName = this.fc.lastName.value;
    user.isTeacher = this.fc.isTeacher.value;

    this.userService.createUser(user, this.fc.password.value).subscribe(response => {
      this.loadUsers();
    });
    this.loading = false;

    this.closeCreateUserModal();
  }

  closeCreateUserModal() {
    this.showCreateUserModal = false;
  }

}
