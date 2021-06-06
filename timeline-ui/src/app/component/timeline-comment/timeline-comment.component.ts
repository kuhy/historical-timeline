import { Component, OnInit } from '@angular/core';
import {TimelineCommentDTO} from "../../dto/timeline-comment-dto";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HistoricalTimelineService} from "../../service/historical-timeline.service";
import {HistoricalEventService} from "../../service/historical-event.service";
import {UserService} from "../../service/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {TimelineCommentService} from "../../service/timeline-comment.service";
import {TimelineCommentCreateDTO} from "../../dto/timeline-comment-create-dto";
import {UserDTO} from "../../dto/user-dto";
import {faEdit, faTrash} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-timeline-comment',
  templateUrl: './timeline-comment.component.html',
  styleUrls: ['./timeline-comment.component.css']
})
export class TimelineCommentComponent implements OnInit {
  faEdit = faEdit
  faTrash = faTrash

  studyGroupId: number
  historicalTimelineId: number
  isTeacher = false
  timelineComments: TimelineCommentDTO[] = []

  updateDTO: TimelineCommentDTO
  currentUser: UserDTO

  createForm: FormGroup
  updateForm: FormGroup

  showUpdateModal = false
  showCreateModal = false
  submitted = false
  loading = false

  constructor(private formBuilder: FormBuilder,
              private timelineCommentService: TimelineCommentService,
              private historicalTimelineService: HistoricalTimelineService,
              private historicalEventService: HistoricalEventService,
              private userService: UserService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
    this.studyGroupId = 0
    this.historicalTimelineId = 0

    this.activatedRoute.paramMap.subscribe(params => {
      this.studyGroupId = Number(params.get('studyGroupId'))
      this.historicalTimelineId = Number(params.get('timelineId'))
    })

    this.updateDTO = new TimelineCommentDTO()
    this.currentUser = new UserDTO()

    this.createForm = this.formBuilder.group({
      text: ['', Validators.required]
    })

    this.updateForm = this.formBuilder.group({
      text: ['', Validators.required]
    })
  }

  ngOnInit(): void {
    this.getTeacherStatus()
    this.loadTimelineComments();
  }

  private getTeacherStatus() {
    this.userService.isTeacher().subscribe(response => {
      this.isTeacher = response
    })
  }

  private loadTimelineComments() {
    this.historicalTimelineService.getHistoricalTimeline(this.historicalTimelineId).subscribe(response => {
      this.timelineComments = response.timelineComments
      this.timelineComments = this.timelineComments.sort(function(a, b) { return b.id - a.id })
    })
  }

  // ========== Delete ==========

  deleteComment(id: number) {
    this.timelineCommentService.deleteTimelineComment(id).subscribe(response => {
      this.loadTimelineComments()
    })
  }

  // ========== Update ==========

  // TODO only owner can change comment

  get formUpdate() {
    return this.updateForm.controls
  }

  updateCommentModal(comment: TimelineCommentDTO) {
    this.closeCreateModal()

    this.updateDTO = comment

    this.updateForm.setValue({
      text: this.updateDTO.text
    })

    this.showUpdateModal = true
    this.submitted = false
  }

  updateComment() {
    this.submitted = true

    if (this.updateForm.invalid) {
      return
    }

    this.loading = true

    this.updateDTO.text = this.formUpdate.text.value
    this.timelineCommentService.updateTimelineComment(this.updateDTO).subscribe(response => {
      this.loadTimelineComments()
    })

    this.loading = false
    this.closeUpdateModal()
  }

  closeUpdateModal() {
    this.updateDTO = new TimelineCommentDTO()
    this.showUpdateModal = false
  }

  // ========== Create ==========

  get formCreate() {
    return this.createForm.controls
  }

  createCommentModal() {
    this.closeUpdateModal()

    this.showCreateModal = true
    this.submitted = false
  }

  createComment() {
    this.submitted = true

    if (this.createForm.invalid) {
      return
    }

    this.loading = true

    // create comment
    let comment = new TimelineCommentCreateDTO()
    comment.text = this.formCreate.text.value
    this.historicalTimelineService.addCommentInTimeline(this.historicalTimelineId, comment).subscribe(response => {
      this.loadTimelineComments()
    })

    // add user to comment TODO
    // this.userService.getLoggedInUser().subscribe(response => {
    //   this.currentUser = response
    // })
    //
    // console.log(this.currentUser)
    //
    // this.updateDTO = new TimelineCommentDTO()
    // this.updateDTO.text = comment.text
    // this.updateDTO.user = this.currentUser

    // this.timelineCommentService.updateTimelineComment(this.updateDTO).subscribe(response => {
    //   this.loadTimelineComments()
    // })

    this.loading = false
    this.closeCreateModal()
  }

  closeCreateModal() {
    this.showCreateModal = false
  }
  logout() {
    this.userService.logout().subscribe(data => {
      this.router.navigate(['/login']);
    });
  }

  // ========== OTHERS ==========

  backToTimeline() {
    this.router.navigate([`/groups/${this.studyGroupId}`]);
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
