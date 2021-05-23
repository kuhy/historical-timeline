import { Component, OnInit } from '@angular/core';
import {HistoricalEventDTO} from "../../dto/historical-event-dto";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HistoricalTimelineService} from "../../service/historical-timeline.service";
import {UserService} from "../../service/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {HistoricalEventService} from "../../service/historical-event.service";
import {HistoricalEventCreateDTO} from "../../dto/historical-event-create-dto";
import {faEdit, faEye, faTrash } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-historical-event',
  templateUrl: './historical-event.component.html',
  styleUrls: ['./historical-event.component.css']
})
export class HistoricalEventComponent implements OnInit {
  faEye = faEye
  faEdit = faEdit
  faTrash = faTrash

  studyGroupId: number
  historicalTimelineId: number
  isTeacher = false
  historicalEvents: HistoricalEventDTO[] = []

  updateDTO: HistoricalEventDTO

  createForm: FormGroup
  updateForm: FormGroup

  showUpdateModal = false
  showCreateModal = false
  submitted = false
  loading = false

  constructor(private formBuilder: FormBuilder,
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

    this.updateDTO = new HistoricalEventDTO()

    this.createForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      date: ['', Validators.required], // TODO date validator
      location: ['', Validators.required] // TODO validator ??
    })

    this.updateForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      date: ['', Validators.required], // TODO date validator
      location: ['', Validators.required] // TODO validator ??
      // TODO image
    })
  }

  ngOnInit(): void {
    this.getTeacherStatus()
    this.loadHistoricalEvents();
  }

  private getTeacherStatus() {
    this.userService.isTeacher().subscribe(response => {
      this.isTeacher = response
    })
  }

  private loadHistoricalEvents() {
    this.historicalTimelineService.getHistoricalTimeline(this.historicalTimelineId).subscribe(response => {
      this.historicalEvents = response.historicalEvents
    })
  }

  // ========== Delete ==========

  deleteHistoricalEvent(id: number) {
    this.historicalEventService.deleteHistoricalEvent(id).subscribe(response => {
      this.loadHistoricalEvents()
    })
  }

  // ========== Update ==========

  get formUpdate() {
    return this.updateForm.controls
  }

  updateHistoricalEventModal(event: HistoricalEventDTO) {
    this.closeCreateModal()

    this.updateDTO = event

    this.updateForm.setValue({
      name: this.updateDTO.name,
      description: this.updateDTO.description,
      date: this.updateDTO.date, // TODO format
      location: this.updateDTO.location
    })

    this.showUpdateModal = true
    this.submitted = false
  }

  updateHistoricalEvent() {
    this.submitted = true

    if (this.updateForm.invalid) {
      return
    }

    this.loading = true

    this.updateDTO.name = this.formUpdate.name.value
    this.updateDTO.description = this.formUpdate.description.value
    this.updateDTO.location = this.formUpdate.location.value
    this.updateDTO.date = this.formUpdate.date.value // TODO different date format
    // this.updateDTO.image = this.formUpdate.image.value // TODO image

    this.historicalEventService.updateHistoricalEvent(this.updateDTO).subscribe(response => {
      this.loadHistoricalEvents()
    })

    this.loading = false
    this.closeUpdateModal()
  }

  closeUpdateModal() {
    this.updateDTO = new HistoricalEventDTO()
    this.showUpdateModal = false
  }

  // ========== Create ==========

  get formCreate() {
    return this.createForm.controls
  }

  createHistoricalEventModal() {
    this.closeUpdateModal()

    this.showCreateModal = true
    this.submitted = false
  }

  createHistoricalEvent() {
    // TODO not working
    this.submitted = true

    if (this.createForm.invalid) {
      return
    }

    this.loading = true

    let event = new HistoricalEventCreateDTO()
    event.name = this.formCreate.name.value
    event.description = this.formCreate.description.value
    event.location = this.formCreate.location.value
    event.date = this.formCreate.date.value // TODO different date format
    this.historicalTimelineService.addEventToTimeline(this.historicalTimelineId, event).subscribe(response => {
      this.loadHistoricalEvents()
    })

    this.loading = false
    this.closeCreateModal()
  }

  closeCreateModal() {
    this.showCreateModal = false
  }

  // ========== OTHERS ==========

  backToTimeline() {
    this.router.navigate([`/groups/${this.studyGroupId}`]);
  }

  showImage(eventId: number) {
    // TODO
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
