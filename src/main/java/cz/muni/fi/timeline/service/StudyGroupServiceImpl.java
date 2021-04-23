package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.dao.StudyGroupDao;
import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
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
        studyGroupDao.create(studyGroup);
    }

    @Override
    public void updateStudyGroup(StudyGroup studyGroup) {
        studyGroupDao.update(studyGroup);
    }

    @Override
    public void removeStudyGroup(StudyGroup studyGroup) {
        studyGroupDao.remove(studyGroup);
    }

    @Override
    public void addUserToStudyGroup(StudyGroup studyGroup, User user) throws AlreadyInStudyGroup {
        StudyGroup group = studyGroupDao.findById(studyGroup.getId()).orElseThrow(() ->
                new IllegalArgumentException("Study group with the given id does not exist.")
        );
        if(group.getUsers().contains(user)){
            throw new AlreadyInStudyGroup("StudyGroup already contains this Student") {
            };
        }
        group.addUser(user);
        studyGroupDao.update(group);
    }

    @Override
    public Optional<StudyGroup> findById(long id) {
        return studyGroupDao.findById(id);
    }

    @Override
    public List<StudyGroup> findAllStudyGroups() {
        return studyGroupDao.findAll();
    }

}
