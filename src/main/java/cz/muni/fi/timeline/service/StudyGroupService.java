package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.service.exception.UserAlreadyInStudyGroupException;
import cz.muni.fi.timeline.service.exception.UserNotInStudyGroupException;

import java.util.List;
import java.util.Optional;

/**
 * Service for manipulation with Study groups
 * @author Matej Macák
 */
public interface StudyGroupService {
    /**
     * @param studyGroup study group to be created
     */
    void createStudyGroup(StudyGroup studyGroup);

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
     * add new user to study group in case the user is not already in
     * @param studyGroup study group to which user will be enrolled
     * @param user student to be enrolled
     * @throws UserAlreadyInStudyGroupException in case User is alreday in studyGroup
     */
    void addUserToStudyGroup(StudyGroup studyGroup, User user) throws UserAlreadyInStudyGroupException;

    /**
     * removes user from study group, if user is in the group
     * @param studyGroup study group from which user is removed
     * @param user user to be removed from study group
     * @throws UserNotInStudyGroupException in case User is not in studyGroup
     */
    void removeUserFromStudyGroup(StudyGroup studyGroup, User user) throws UserNotInStudyGroupException;

    /**
     * finds study group based on id
     * @param id id of the study group
     * @return study group with requested id
     */
    Optional<StudyGroup> findById(long id);

    /**
     *finds all study groups from databasae
     * @return all study groups
     */
    List<StudyGroup> findAllStudyGroups();
}
