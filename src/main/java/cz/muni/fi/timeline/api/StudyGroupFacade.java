package cz.muni.fi.timeline.api;

import cz.muni.fi.timeline.api.dto.StudyGroupCreateDTO;
import cz.muni.fi.timeline.api.dto.StudyGroupDTO;
import cz.muni.fi.timeline.api.dto.StudyGroupNewNameDTO;
import cz.muni.fi.timeline.service.UserAlreadyInStudyGroupException;

import java.util.List;

/**
 * Facade for study groups.
 * @author Tri Le Mau
 */
public interface StudyGroupFacade {

    public Long createStudyGroup(StudyGroupCreateDTO studyGroup);

    public StudyGroupDTO getStudyGroupWithId(Long id);

    public void newStudyGroupName(StudyGroupNewNameDTO newName);

    public void removeStudyGroup(Long studyGroupId);

    public void addUserToStudyGroup(Long studyGroupId, Long userId) throws UserAlreadyInStudyGroupException;

//    public void removeUserFromStudyGroup(Long studyGroupId, Long userId); TODO

    public List<StudyGroupDTO> getAllStudyGroups();

}
