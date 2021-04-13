package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.entity.User;

public interface StudyGroupService {
    void createStudyGroup(StudyGroup studyGroup, User teacher);
    void updateStudyGroup(StudyGroup studyGroup);
    void removeStudyGroup(StudyGroup studyGroup);
    // enroll student to study group
    // void getAllUserStudyGroups
    // get study group by id (with timelines)
}
