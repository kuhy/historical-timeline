import {Component, OnInit} from '@angular/core';
import {StudyGroupService} from "../../service/study-group.service";
import {StudyGroupDTO} from "../../dto/study-group-dto";

@Component({
  selector: 'app-study-groups',
  templateUrl: './study-group.component.html',
  styleUrls: ['./study-group.component.css']
})
export class StudyGroupComponent implements OnInit {
  studyGroups: StudyGroupDTO[] = [];
  tmp: any;

  constructor(private studyGroupService: StudyGroupService) { }

  ngOnInit(): void {
    this.loadStudyGroups();
  }

  private loadStudyGroups() {
    this.studyGroupService.getAllGroups().subscribe(response => {
      this.tmp= response;
      this.studyGroups = this.tmp.content;
    })
  }
}
