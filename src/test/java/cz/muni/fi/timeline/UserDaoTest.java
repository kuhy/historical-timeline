package cz.muni.fi.timeline;

import cz.muni.fi.timeline.dao.StudyGroupDao;
import cz.muni.fi.timeline.dao.UserDao;
import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.entity.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

/**
 * Tests for UserDao.
 *
 * @author Karolína Veselá
 */
@ContextConfiguration(classes = HistoricalTimelineApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class UserDaoTest extends AbstractTestNGSpringContextTests {
    @Inject
    private UserDao userDao;

    @Inject
    private StudyGroupDao studyGroupDao;

    private StudyGroup englishHistoryGroup;
    private StudyGroup asianHistoryGroup;

    @BeforeMethod
    @Transactional
    public void before() {
        englishHistoryGroup = new StudyGroup();
        englishHistoryGroup.setName("English history group");

        asianHistoryGroup = new StudyGroup();
        asianHistoryGroup.setName("Asian history group");

        studyGroupDao.create(englishHistoryGroup);
        studyGroupDao.create(asianHistoryGroup);
    }

    @Test
    @Transactional
    public void testCreateUser() {
        User user = new User();
        user.setFirstName("Thrall");
        user.setLastName("Hellscream");
        user.setUsername("Warchief");
        user.setHashedPassword("1234");
        user.setIsTeacher(true);
        user.addStudyGroup(englishHistoryGroup);
        user.addStudyGroup(asianHistoryGroup);

        Assert.assertEquals(userDao.findAll().size(), 0);

        userDao.create(user);

        Assert.assertEquals(userDao.findAll().size(), 1);
    }

    @Test(expectedExceptions = PersistenceException.class)
    @Transactional
    public void testCreateUserNullFirstName() {
        User user = new User();
        user.setLastName("Hellscream");
        user.setUsername("Warchief");
        user.setHashedPassword("1234");
        user.setIsTeacher(true);
        user.addStudyGroup(englishHistoryGroup);
        user.addStudyGroup(asianHistoryGroup);

        userDao.create(user);
    }

    @Test(expectedExceptions = PersistenceException.class)
    @Transactional
    public void testCreateUserNullLastName() {
        User user = new User();
        user.setFirstName("Thrall");
        user.setUsername("Warchief");
        user.setHashedPassword("1234");
        user.setIsTeacher(true);
        user.addStudyGroup(englishHistoryGroup);
        user.addStudyGroup(asianHistoryGroup);

        userDao.create(user);
    }

    @Test(expectedExceptions = PersistenceException.class)
    @Transactional
    public void testCreateUserNullUsername() {
        User user = new User();
        user.setFirstName("Thrall");
        user.setLastName("Hellscream");
        user.setHashedPassword("1234");
        user.setIsTeacher(true);
        user.addStudyGroup(englishHistoryGroup);
        user.addStudyGroup(asianHistoryGroup);

        userDao.create(user);
    }

    @Test(expectedExceptions = PersistenceException.class)
    @Transactional
    public void testCreateUserNullHashedPassword() {
        User user = new User();
        user.setFirstName("Thrall");
        user.setLastName("Hellscream");
        user.setUsername("Warchief");
        user.setIsTeacher(true);
        user.addStudyGroup(englishHistoryGroup);
        user.addStudyGroup(asianHistoryGroup);

        userDao.create(user);
    }

    @Test(expectedExceptions = PersistenceException.class)
    @Transactional
    public void testCreateUserNullIsTeacher() {
        User user = new User();
        user.setFirstName("Thrall");
        user.setLastName("Hellscream");
        user.setUsername("Warchief");
        user.setHashedPassword("1234");
        user.addStudyGroup(englishHistoryGroup);
        user.addStudyGroup(asianHistoryGroup);

        userDao.create(user);
    }

    @Test
    @Transactional
    public void testFindAllUsers() {
        User teacher = new User();
        teacher.setFirstName("Thrall");
        teacher.setLastName("Hellscream");
        teacher.setUsername("Warchief");
        teacher.setHashedPassword("1234");
        teacher.setIsTeacher(true);
        teacher.addStudyGroup(asianHistoryGroup);

        User student = new User();
        student.setFirstName("David");
        student.setLastName("Lister");
        student.setUsername("DavidLister");
        student.setHashedPassword("1234");
        student.setIsTeacher(false);
        student.addStudyGroup(englishHistoryGroup);

        Assert.assertEquals(userDao.findAll().size(), 0);

        userDao.create(teacher);
        userDao.create(student);

        Assert.assertEquals(userDao.findAll().size(), 2);
    }

    @Test
    @Transactional
    public void testFindAllStudents() {
        User teacher1 = new User();
        teacher1.setFirstName("Thrall");
        teacher1.setLastName("Hellscream");
        teacher1.setUsername("Warchief");
        teacher1.setHashedPassword("1234");
        teacher1.setIsTeacher(true);
        teacher1.addStudyGroup(englishHistoryGroup);
        teacher1.addStudyGroup(asianHistoryGroup);

        User teacher2 = new User();
        teacher2.setFirstName("Jožko");
        teacher2.setLastName("Pročko");
        teacher2.setUsername("JozkoProcko");
        teacher2.setHashedPassword("passHash1");
        teacher2.setIsTeacher(true);
        teacher2.addStudyGroup(asianHistoryGroup);

        User student1 = new User();
        student1.setFirstName("David");
        student1.setLastName("Lister");
        student1.setUsername("DavidLister");
        student1.setHashedPassword("1234");
        student1.setIsTeacher(false);
        student1.addStudyGroup(englishHistoryGroup);
        student1.addStudyGroup(asianHistoryGroup);

        User student2 = new User();
        student2.setFirstName("Alena");
        student2.setLastName("Kováčova");
        student2.setUsername("AlenaKovacova");
        student2.setHashedPassword("passHash2");
        student2.setIsTeacher(false);
        student2.addStudyGroup(englishHistoryGroup);

        Assert.assertEquals(userDao.findAllStudents().size(), 0);

        userDao.create(teacher1);
        userDao.create(teacher2);
        userDao.create(student1);
        userDao.create(student2);

        Assert.assertEquals(userDao.findAllStudents().size(), 2);
    }

    @Test
    @Transactional
    public void testFindAllTeacher() {
        User teacher1 = new User();
        teacher1.setFirstName("Thrall");
        teacher1.setLastName("Hellscream");
        teacher1.setUsername("Warchief");
        teacher1.setHashedPassword("1234");
        teacher1.setIsTeacher(true);
        teacher1.addStudyGroup(englishHistoryGroup);
        teacher1.addStudyGroup(asianHistoryGroup);

        User teacher2 = new User();
        teacher2.setFirstName("Jožko");
        teacher2.setLastName("Pročko");
        teacher2.setUsername("JozkoProcko");
        teacher2.setHashedPassword("passHash1");
        teacher2.setIsTeacher(true);
        teacher2.addStudyGroup(asianHistoryGroup);

        User student1 = new User();
        student1.setFirstName("David");
        student1.setLastName("Lister");
        student1.setUsername("DavidLister");
        student1.setHashedPassword("1234");
        student1.setIsTeacher(false);
        student1.addStudyGroup(englishHistoryGroup);
        student1.addStudyGroup(asianHistoryGroup);

        User student2 = new User();
        student2.setFirstName("Alena");
        student2.setLastName("Kováčova");
        student2.setUsername("AlenaKovacova");
        student2.setHashedPassword("passHash2");
        student2.setIsTeacher(false);
        student2.addStudyGroup(englishHistoryGroup);

        Assert.assertEquals(userDao.findAllTeachers().size(), 0);

        userDao.create(teacher1);
        userDao.create(teacher2);
        userDao.create(student1);
        userDao.create(student2);

        Assert.assertEquals(userDao.findAllTeachers().size(), 2);
    }

    @Test
    @Transactional
    public void testFindUserByExistingId () {
        User user = new User();
        user.setFirstName("Thrall");
        user.setLastName("Hellscream");
        user.setUsername("Warchief");
        user.setHashedPassword("1234");
        user.setIsTeacher(true);
        user.addStudyGroup(englishHistoryGroup);
        user.addStudyGroup(asianHistoryGroup);

        userDao.create(user);

        Optional<User> find = userDao.findById(user.getId());

        Assert.assertTrue(find.isPresent());
        Assert.assertEquals(find.get().getFirstName(), "Thrall");
        Assert.assertEquals(find.get().getLastName(), "Hellscream");
        Assert.assertEquals(find.get().getUsername(), "Warchief");
        Assert.assertEquals(find.get().getHashedPassword(), "1234");
        Assert.assertTrue(find.get().getIsTeacher());
        assertUserContainsStudyGroup(find.get().getStudyGroups(), englishHistoryGroup);
        assertUserContainsStudyGroup(find.get().getStudyGroups(), asianHistoryGroup);
    }

    @Test
    @Transactional
    public void testFindUserByNonExistingId() {
        Assert.assertFalse(userDao.findById(1L).isPresent());
    }

    @Test
    @Transactional
    public void testFindUserByExistingUsername () {
        User user = new User();
        user.setFirstName("Thrall");
        user.setLastName("Hellscream");
        user.setUsername("Warchief");
        user.setHashedPassword("1234");
        user.setIsTeacher(true);
        user.addStudyGroup(englishHistoryGroup);
        user.addStudyGroup(asianHistoryGroup);

        userDao.create(user);

        Optional<User> find = userDao.findByUserName("Warchief");

        Assert.assertTrue(find.isPresent());
        Assert.assertEquals(find.get().getFirstName(), "Thrall");
        Assert.assertEquals(find.get().getLastName(), "Hellscream");
        Assert.assertEquals(find.get().getUsername(), "Warchief");
        Assert.assertEquals(find.get().getHashedPassword(), "1234");
        Assert.assertTrue(find.get().getIsTeacher());
        assertUserContainsStudyGroup(find.get().getStudyGroups(), englishHistoryGroup);
        assertUserContainsStudyGroup(find.get().getStudyGroups(), asianHistoryGroup);
    }

    @Test
    @Transactional
    public void testFindUserByNonExistingUsername () {
        Assert.assertFalse(userDao.findByUserName("NonExistingUsername").isPresent());
    }

    @Test
    @Transactional
    public void testUpdateUser () {
        User user = new User();
        user.setFirstName("Thrall");
        user.setLastName("Hellscream");
        user.setUsername("Warchief");
        user.setHashedPassword("1234");
        user.setIsTeacher(true);
        user.addStudyGroup(englishHistoryGroup);
        user.addStudyGroup(asianHistoryGroup);

        userDao.create(user);

        user.setFirstName("Jozef");
        user.setLastName("Kolo");
        user.setUsername("JozefKolo");
        user.setHashedPassword("newPassHash1");
        user.setIsTeacher(false);
        user.removeStudyGroup(asianHistoryGroup);

        userDao.update(user);

        Optional<User> find = userDao.findById(user.getId());
        Assert.assertTrue(find.isPresent());
        Assert.assertEquals(find.get().getFirstName(), "Jozef");
        Assert.assertEquals(find.get().getLastName(), "Kolo");
        Assert.assertEquals(find.get().getUsername(), "JozefKolo");
        Assert.assertEquals(find.get().getHashedPassword(), "newPassHash1");
        Assert.assertFalse(find.get().getIsTeacher());
        assertUserContainsStudyGroup(find.get().getStudyGroups(), englishHistoryGroup);
    }

    @Test
    @Transactional
    public void testRemoveUser () {
        User user = new User();
        user.setFirstName("Thrall");
        user.setLastName("Hellscream");
        user.setUsername("Warchief");
        user.setHashedPassword("1234");
        user.setIsTeacher(true);
        user.addStudyGroup(englishHistoryGroup);
        user.addStudyGroup(asianHistoryGroup);

        userDao.create(user);

        Assert.assertEquals(userDao.findAll().size(), 1);

        userDao.remove(user);

        Assert.assertEquals(userDao.findAll().size(), 0);
    }

    private void assertUserContainsStudyGroup(Set<StudyGroup> studyGroups, StudyGroup expectedStudyGroup) {
        for (StudyGroup studyGroup : studyGroups) {
            if (studyGroup.equals(expectedStudyGroup)) {
                return;
            }
        }

        Assert.fail("Could not find study group " + expectedStudyGroup.getName() + " in collection " + studyGroups);
    }
}

