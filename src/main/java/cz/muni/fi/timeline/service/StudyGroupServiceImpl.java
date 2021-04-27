package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.dao.StudyGroupDao;
import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.service.exception.ServiceLayerException;
import cz.muni.fi.timeline.service.exception.UserAlreadyInStudyGroupException;
import cz.muni.fi.timeline.service.exception.UserNotInStudyGroupException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of StudyGroupService
 * @author Matej Macák
 */
@Service
public class StudyGroupServiceImpl implements StudyGroupService{

    private final StudyGroupDao studyGroupDao;

    @Inject
    public StudyGroupServiceImpl(StudyGroupDao studyGroupDao) {
        this.studyGroupDao = studyGroupDao;
    }

    @Override
    public void createStudyGroup(StudyGroup studyGroup) {

        try {
            studyGroupDao.create(studyGroup);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void updateStudyGroup(StudyGroup studyGroup) {
        try {
            studyGroupDao.update(studyGroup);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void removeStudyGroup(StudyGroup studyGroup) {
        try {
            studyGroupDao.remove(studyGroup);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void addUserToStudyGroup(StudyGroup studyGroup, User user) throws UserAlreadyInStudyGroupException {
        try {
            StudyGroup group = studyGroupDao.findById(studyGroup.getId()).orElseThrow(() ->
                new ServiceLayerException("Study group with the given id does not exist.")
            );

            if(group.getUsers().contains(user)){
                throw new UserAlreadyInStudyGroupException("StudyGroup already contains this User");
            }

            group.addUser(user);
            studyGroupDao.update(group);
        } catch (UserAlreadyInStudyGroupException e) {
            throw e;
        } catch (ServiceLayerException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void removeUserFromStudyGroup(StudyGroup studyGroup, User user) throws UserNotInStudyGroupException {
        try {
            StudyGroup group = studyGroupDao.findById(studyGroup.getId()).orElseThrow(() ->
                new ServiceLayerException("Study group with the given id does not exist.")
            );

            if(!group.getUsers().contains(user)){
                throw new UserNotInStudyGroupException("StudyGroup does not contains User.");
            }

            group.removeUser(user);
            studyGroupDao.update(group);
        } catch (UserNotInStudyGroupException e) {
            throw e;
        } catch (ServiceLayerException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public Optional<StudyGroup> findById(long id) {
        try {
            return studyGroupDao.findById(id);
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public List<StudyGroup> findAllStudyGroups() {
        try {
            return studyGroupDao.findAll();
        } catch (Exception e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }
}
