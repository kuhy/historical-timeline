package cz.muni.fi.timeline.api;

import cz.muni.fi.timeline.api.dto.StudyGroupCreateDTO;
import cz.muni.fi.timeline.api.dto.StudyGroupDTO;
import cz.muni.fi.timeline.api.exception.UserAlreadyInStudyGroupException;
import cz.muni.fi.timeline.api.exception.UserNotInStudyGroupException;

import java.util.List;
import java.util.Optional;

/**
 * Facade for study groups.
 * @author Tri Le Mau
 */
public interface StudyGroupFacade {

    /**
     * Creates a study group.
     *
     * @param studyGroup study group used for creating
     * @return id of created study group
     */
    Long createStudyGroup(StudyGroupCreateDTO studyGroup);

    /**
     * Get study group with Id.
     *
     * @param studyGroupId study group id
     * @return study group if found, null otherwise
     */
    Optional<StudyGroupDTO> getStudyGroupWithId(Long studyGroupId);

    /**
     * Updates Study Group.
     *
     * @param studyGroup study group DTO to be updated
     * @return id of updated study group
     */
    Long updateStudyGroup(StudyGroupDTO studyGroup);

    /**
     * Removes study group with Id.
     *
     * @param studyGroupId study group id
     */
    void removeStudyGroup(Long studyGroupId);

    /**
     * Adds user to study group.
     *
     * @param studyGroupId study group id
     * @param userId user id
     * @throws UserAlreadyInStudyGroupException exception is thrown if User is already in Study Group
     */
    void addUserToStudyGroup(Long studyGroupId, Long userId) throws UserAlreadyInStudyGroupException;

    /**
     * Removes user from study group.
     *
     * @param studyGroupId study group id
     * @param userId user id
     * @throws UserNotInStudyGroupException exception is thrown if User is not in Study Group
     */
    void removeUserFromStudyGroup(Long studyGroupId, Long userId) throws UserNotInStudyGroupException;

    /**
     * Get all study groups.
     *
     * @return all study groups
     */
    List<StudyGroupDTO> getAllStudyGroups();
}
