package cz.muni.fi.timeline;



import cz.muni.fi.timeline.dao.StudyGroupDao;

import cz.muni.fi.timeline.entity.StudyGroup;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Tests for StudyGroupDao.
 *
 * @author Karolína Veselá
 */
@ContextConfiguration(classes = HistoricalTimelineApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class StudyGroupDaoTest extends AbstractTestNGSpringContextTests {
    @Inject
    private StudyGroupDao studyGroupDao;


    @Test
    @Transactional
    public void testCreateStudyGroup() {
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName("History");

        Assert.assertEquals(studyGroupDao.findAll().size(), 0);

        studyGroupDao.create(studyGroup);

        Assert.assertEquals(studyGroupDao.findAll().size(), 1);
    }

    @Test(expectedExceptions = PersistenceException.class)
    @Transactional
    public void testCreateStudyGroupNullName() {
        StudyGroup studyGroup = new StudyGroup();

        studyGroupDao.create(studyGroup);
        studyGroupDao.findAll();
    }

    @Test
    @Transactional
    public void testFindAllStudyGroups() {
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName("History");
        studyGroupDao.create(studyGroup);

        StudyGroup studyGroup2 = new StudyGroup();
        studyGroup2.setName("History2");
        studyGroupDao.create(studyGroup2);

        Assert.assertEquals(studyGroupDao.findAll().size(), 2);
    }

    @Test
    @Transactional
    public void testFindStudyGroupById() {
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName("History");
        studyGroupDao.create(studyGroup);

        Optional<StudyGroup> group = studyGroupDao.findById(studyGroup.getId());
        Assert.assertTrue(group.isPresent());
        Assert.assertEquals(group.get().getName(), "History");

        Assert.assertFalse(studyGroupDao.findById(studyGroup.getId() + 1).isPresent());
    }

    @Test
    @Transactional
    public void testFindStudyGroupByName() {
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName("History");
        studyGroupDao.create(studyGroup);

        StudyGroup studyGroup2 = new StudyGroup();
        studyGroup2.setName("History2");
        studyGroupDao.create(studyGroup2);


        List<StudyGroup> group = studyGroupDao.findByName("History2");
        Assert.assertEquals(group.size(), 1);
        Assert.assertEquals(group.get(0).getName(), "History2");
    }

    @Test
    @Transactional
    public void testUpdateStudyGroup() {
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName("History");
        studyGroupDao.create(studyGroup);
        studyGroup.setName("History2");

        studyGroupDao.update(studyGroup);

        Optional<StudyGroup> find = studyGroupDao.findById(studyGroup.getId());
        Assert.assertTrue(find.isPresent());
        Assert.assertEquals(find.get().getName(), "History2");
    }

    @Test
    @Transactional
    public void testRemoveStudyGroup() {
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName("History");

        studyGroupDao.create(studyGroup);

        Assert.assertEquals(studyGroupDao.findAll().size(), 1);

        studyGroupDao.remove(studyGroup);

        Assert.assertEquals(studyGroupDao.findAll().size(), 0);
    }

}
