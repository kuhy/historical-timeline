package cz.muni.fi.timeline.api;

import cz.muni.fi.timeline.api.dto.StudyGroupCreateDTO;
import cz.muni.fi.timeline.api.dto.StudyGroupDTO;
import cz.muni.fi.timeline.api.dto.StudyGroupNewNameDTO;
import cz.muni.fi.timeline.service.UserAlreadyInStudyGroupException;
import cz.muni.fi.timeline.service.UserNotInStudyGroupException;

import java.util.List;

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
    public Long createStudyGroup(StudyGroupCreateDTO studyGroup);

    /**
     * Get study group with Id.
     *
     * @param studyGroupId study group id
     * @return study group if found, null otherwise
     */
    public StudyGroupDTO getStudyGroupWithId(Long studyGroupId);

    /**
     * Sets new study group name.
     *
     * @param newName new study group name
     */
    public void newStudyGroupName(StudyGroupNewNameDTO newName);

    /**
     * Removes study group with Id.
     *
     * @param studyGroupId study group id
     */
    public void removeStudyGroup(Long studyGroupId);

    /**
     * Adds user to study group.
     *
     * @param studyGroupId study group id
     * @param userId user id
     * @throws UserAlreadyInStudyGroupException exception is thrown if User is already in Study Group
     */
    public void addUserToStudyGroup(Long studyGroupId, Long userId) throws UserAlreadyInStudyGroupException;

    /**
     * Removes user from study group.
     *
     * @param studyGroupId study group id
     * @param userId user id
     * @throws UserNotInStudyGroupException exception is thrown if User is not in Study Group
     */
    public void removeUserFromStudyGroup(Long studyGroupId, Long userId) throws UserNotInStudyGroupException;

    /**
     * Get all study groups.
     *
     * @return all study groups
     */
    public List<StudyGroupDTO> getAllStudyGroups();

}
