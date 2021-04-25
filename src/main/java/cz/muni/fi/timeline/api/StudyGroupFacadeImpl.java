package cz.muni.fi.timeline.api;

import cz.muni.fi.timeline.api.dto.StudyGroupCreateDTO;
import cz.muni.fi.timeline.api.dto.StudyGroupDTO;
import cz.muni.fi.timeline.api.dto.StudyGroupNewNameDTO;
import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.service.StudyGroupService;
import cz.muni.fi.timeline.service.UserAlreadyInStudyGroupException;
import cz.muni.fi.timeline.service.UserNotInStudyGroupException;
import cz.muni.fi.timeline.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
public class StudyGroupFacadeImpl implements StudyGroupFacade {
    final static Logger log = LoggerFactory.getLogger(StudyGroupFacadeImpl.class);

    final private StudyGroupService studyGroupService;
    final private UserService userService;
    final private BeanMappingService beanMappingService;

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
    public StudyGroupDTO getStudyGroupWithId(Long id) {
        Optional<StudyGroup> find = studyGroupService.findById(id);
        return find.isPresent() ? beanMappingService.mapTo(find.get(), StudyGroupDTO.class) : null;
    }

    @Override
    public void newStudyGroupName(StudyGroupNewNameDTO newName) {
        StudyGroup studyGroup = studyGroupService.findById(newName.getId()).orElseThrow(() ->
            new IllegalArgumentException("Study group with given id does not exist.")
        );

        studyGroup.setName(newName.getName());
        studyGroupService.updateStudyGroup(studyGroup);
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
