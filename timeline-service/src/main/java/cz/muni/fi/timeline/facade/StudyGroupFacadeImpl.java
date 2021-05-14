package cz.muni.fi.timeline.facade;

import cz.muni.fi.timeline.api.StudyGroupFacade;
import cz.muni.fi.timeline.api.dto.StudyGroupCreateDTO;
import cz.muni.fi.timeline.api.dto.StudyGroupDTO;
import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.mapper.BeanMappingService;
import cz.muni.fi.timeline.service.StudyGroupService;
import cz.muni.fi.timeline.api.exception.UserAlreadyInStudyGroupException;
import cz.muni.fi.timeline.api.exception.UserNotInStudyGroupException;
import cz.muni.fi.timeline.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Implementation for StudyGroupFacade.
 *
 * @author Tri Le Mau
 */
@Service
@Transactional
public class StudyGroupFacadeImpl implements StudyGroupFacade {

    private final StudyGroupService studyGroupService;
    private final UserService userService;
    private final BeanMappingService beanMappingService;

    @Inject
    public StudyGroupFacadeImpl(StudyGroupService studyGroupService, UserService userService, BeanMappingService beanMappingService) {
        this.studyGroupService = studyGroupService;
        this.userService = userService;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public Long createStudyGroup(StudyGroupCreateDTO studyGroup) {
        StudyGroup mappedStudyGroup = beanMappingService.mapTo(studyGroup, StudyGroup.class);
        studyGroupService.createStudyGroup(mappedStudyGroup);
        return mappedStudyGroup.getId();
    }

    @Override
    public Optional<StudyGroupDTO> getStudyGroupWithId(Long studyGroupId) {
        Optional<StudyGroup> find = studyGroupService.findById(studyGroupId);

        if (find.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(beanMappingService.mapTo(find.get(), StudyGroupDTO.class));
    }

    @Override
    public Long updateStudyGroup(StudyGroupDTO studyGroup) {
        StudyGroup mappedStudyGroup = beanMappingService.mapTo(studyGroup, StudyGroup.class);
        studyGroupService.updateStudyGroup(mappedStudyGroup);
        return mappedStudyGroup.getId();
    }

    @Override
    public void removeStudyGroup(Long studyGroupId) {
        StudyGroup studyGroup = studyGroupService.findById(studyGroupId).orElseThrow(() ->
            new IllegalArgumentException("Study group with given id does not exist.")
        );

        studyGroupService.removeStudyGroup(studyGroup);
    }

    @Override
    public void addUserToStudyGroup(Long studyGroupId, Long userId) throws UserAlreadyInStudyGroupException {
        StudyGroup studyGroup = studyGroupService.findById(studyGroupId).orElseThrow(() ->
            new IllegalArgumentException("Study group with given id does not exist.")
        );

        User user = userService.findById(userId).orElseThrow(() ->
            new IllegalArgumentException("User with given id does not exist.")
        );

        studyGroupService.addUserToStudyGroup(studyGroup, user);
    }

    @Override
    public void removeUserFromStudyGroup(Long studyGroupId, Long userId) throws UserNotInStudyGroupException {
        StudyGroup studyGroup = studyGroupService.findById(studyGroupId).orElseThrow(() ->
                new IllegalArgumentException("Study group with given id does not exist.")
        );

        User user = userService.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User with given id does not exist.")
        );

        studyGroupService.removeUserFromStudyGroup(studyGroup, user);
    }

    @Override
    public List<StudyGroupDTO> getAllStudyGroups() {
        return beanMappingService.mapTo(studyGroupService.findAllStudyGroups(), StudyGroupDTO.class);
    }
}
