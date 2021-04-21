package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.dao.StudyGroupDao;
import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

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
    public void addUserToStudyGroup(StudyGroup studyGroup, User user) throws ContainsStudentException {
        if(studyGroup.getUsers().contains(user)){
            throw new ContainsStudentException("StudyGroup already contains this Student") {
            };
        }
        studyGroup.addUser(user);
        studyGroupDao.update(studyGroup);
    }

    @Override
    public Optional<StudyGroup> findById(long id) {
        return studyGroupDao.findById(id);
    }

}
