package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.dao.StudyGroupDao;
import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.api.exception.UserAlreadyInStudyGroupException;
import cz.muni.fi.timeline.api.exception.UserNotInStudyGroupException;
import cz.muni.fi.timeline.service.exception.ServiceLayerException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of StudyGroupService
 * @author Matej Macák
 */
@Service
public class StudyGroupServiceImpl implements StudyGroupService {

    private final StudyGroupDao studyGroupDao;

    @Inject
    public StudyGroupServiceImpl(StudyGroupDao studyGroupDao) {
        this.studyGroupDao = studyGroupDao;
    }

    @Override
    public void createStudyGroup(StudyGroup studyGroup) {
        if (studyGroup == null) {
            throw new IllegalArgumentException("Study group is null.");
        }

        try {
            studyGroupDao.create(studyGroup);
        } catch (DataAccessException e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void updateStudyGroup(StudyGroup studyGroup) {
        if (studyGroup == null) {
            throw new IllegalArgumentException("Study group is null.");
        }

        try {
            studyGroupDao.update(studyGroup);
        } catch (DataAccessException e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void removeStudyGroup(StudyGroup studyGroup) {
        if (studyGroup == null) {
            throw new IllegalArgumentException("Study group is null.");
        }

        try {
            studyGroupDao.remove(studyGroup);
        } catch (DataAccessException e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void addUserToStudyGroup(StudyGroup studyGroup, User user) throws UserAlreadyInStudyGroupException {
        if (studyGroup == null) {
            throw new IllegalArgumentException("Study group is null.");
        }

        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }

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
        } catch (DataAccessException e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public void removeUserFromStudyGroup(StudyGroup studyGroup, User user) throws UserNotInStudyGroupException {
        if (studyGroup == null) {
            throw new IllegalArgumentException("Study group is null.");
        }

        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }

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
        } catch (DataAccessException e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public Optional<StudyGroup> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is null.");
        }

        try {
            Optional<StudyGroup> group = studyGroupDao.findById(id);
            return group;
        } catch (DataAccessException e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }

    @Override
    public List<StudyGroup> findAllStudyGroups() {
        try {
            return studyGroupDao.findAll();
        } catch (DataAccessException e) {
            throw new ServiceLayerException(e.getMessage());
        }
    }
}
