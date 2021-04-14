package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * @author Matej Macák
 */
@Service
public interface StudyGroupService {
    /**
     * @param studyGroup study group to be created
     * @param teacher teacher who created the study group
     */
    void createStudyGroup(StudyGroup studyGroup, User teacher);

    /**
     * updates study group
     * @param studyGroup study group that is updated
     */
    void updateStudyGroup(StudyGroup studyGroup);

    /**
     * removes study group
     * @param studyGroup study group be deleted
     */
    void removeStudyGroup(StudyGroup studyGroup);

    /**
     * add new student to study group in case the student is not already in
     * @param studyGroup study group to which student will be enrolled
     * @param student student to be enrolled
     */
    void addStudentToStudyGroup(StudyGroup studyGroup, User student);

    /**
     * finds study group based on id
     * @param id id of the study group
     * @return study group with requested id
     */
    Optional<StudyGroup> findById(long id);

    /**
     * returns all users (student and teachers from study group
     * @param studyGroup study group from which we want the users
     * @return all users of the study group
     */
    Set<User> getAllUsersStudyGroup(StudyGroup studyGroup);
}
