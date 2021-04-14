package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface StudyGroupService {
    void createStudyGroup(StudyGroup studyGroup, User teacher);
    void updateStudyGroup(StudyGroup studyGroup);
    void removeStudyGroup(StudyGroup studyGroup);
    void addStudentToStudyGroup(StudyGroup studyGroup, User student);
    Optional<StudyGroup> findById(long id);
    Set<User> getAllUsersStudyGroup(StudyGroup studyGroup);
}
