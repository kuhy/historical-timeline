import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HistoricalTimelineDTO} from "../../dto/historical-timeline-dto";
import {HistoricalTimelineService} from "../../service/historical-timeline.service";
import {HistoricalTimelineCreateDTO} from "../../dto/historical-timeline-create-dto";
import {UserService} from "../../service/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {StudyGroupService} from "../../service/study-group.service";

@Component({
  selector: 'app-historical-timeline',
  templateUrl: './historical-timeline.component.html',
  styleUrls: ['./historical-timeline.component.css']
})
export class HistoricalTimelineComponent implements OnInit {

  studyGroupId: number
  isTeacher = false
  historicalTimelines: HistoricalTimelineDTO[] = []

  updateHistoricalTimelineDTO: HistoricalTimelineDTO

  createForm: FormGroup
  updateForm: FormGroup

  showUpdateHistoricalTimelineModal = false
  showCreateHistoricalTimelineModal = false
  loading = false
  submitted = false

  constructor(private formBuilder: FormBuilder,
              private historicalTimelineService: HistoricalTimelineService,
              private studyGroupService: StudyGroupService,
              private userService: UserService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
    this.studyGroupId = 0

    this.activatedRoute.paramMap.subscribe(params => {
      this.studyGroupId = Number(params.get('studyGroupId'))
    })

    this.updateHistoricalTimelineDTO = new HistoricalTimelineDTO()

    this.createForm = this.formBuilder.group({
      name: ['', Validators.required]
    })

    this.updateForm = this.formBuilder.group({
      name: ['', Validators.required]
    })
  }

  ngOnInit(): void {
    this.getTeacherStatus()
    this.loadHistoricalTimelines()
  }

  private getTeacherStatus() {
    this.userService.isTeacher().subscribe(response => {
      this.isTeacher = response
    })
  }

  private loadHistoricalTimelines() {
    this.studyGroupService.getGroup(this.studyGroupId).subscribe(response => {
      this.historicalTimelines = response.historicalTimelines
    })
  }

  // ========== Delete ==========

  deleteHistoricalTimeline(id: number) {
    this.historicalTimelineService.deleteHistoricalTimeline(id).subscribe(response => {
      this.loadHistoricalTimelines()
    })
  }

  // ========== Update ==========

  get formUpdate() {
    return this.updateForm.controls
  }

  updateHistoricalTimelineModal(id: number) {
    this.closeCreateHistoricalTimelineModal()

    this.updateHistoricalTimelineDTO.id = id

    this.showUpdateHistoricalTimelineModal = true
    this.submitted = false
  }

  updateHistoricalTimeline() {
    this.submitted = true

    if (this.updateForm.invalid) {
      return
    }

    this.loading = true

    this.updateHistoricalTimelineDTO.name = this.formUpdate.name.value
    this.historicalTimelineService.updateHistoricalTimeline(this.updateHistoricalTimelineDTO).subscribe(response => {
      this.loadHistoricalTimelines()
    })

    this.loading = false
    this.closeUpdateHistoricalTimelineModal()
  }

  closeUpdateHistoricalTimelineModal() {
    this.updateHistoricalTimelineDTO = new HistoricalTimelineDTO()
    this.showUpdateHistoricalTimelineModal = false
  }

  // ========== Create ==========

  get formCreate() {
    return this.createForm.controls
  }

  createHistoricalTimelineModal() {
    this.closeUpdateHistoricalTimelineModal()

    this.showCreateHistoricalTimelineModal = true
    this.submitted = false
  }

  createHistoricalTimeline() {
    this.submitted = true

    if (this.createForm.invalid) {
      return
    }

    this.loading = true

    let historicalTimeline = new HistoricalTimelineCreateDTO()
    historicalTimeline.name = this.formCreate.name.value
    this.studyGroupService.addHistoricalTimeline(this.studyGroupId, historicalTimeline).subscribe(resposne => {
      this.loadHistoricalTimelines()
    })

    this.loading = false
    this.closeCreateHistoricalTimelineModal()
  }

  closeCreateHistoricalTimelineModal() {
    this.showCreateHistoricalTimelineModal = false
  }

  // ========== Back to Study Groups ==========

  backToStudyGroups() {
    this.router.navigate([`/groups`]);
  }

  // ========== Show Historical Events ==========

  showHistoricalEvents(timelineId: number) {
    this.router.navigate([`/groups/${this.studyGroupId}/timeline/${timelineId}`]);
  }

  // ========== Show Comments ==========

  showComments(timelineId: number) {
    // TODO
  }
}
