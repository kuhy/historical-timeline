package cz.muni.fi.timeline;

import cz.muni.fi.timeline.dao.TeacherDao;
import cz.muni.fi.timeline.entity.Teacher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.Optional;

@ContextConfiguration(classes = HistoricalTimelineApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class TeacherDaoTest extends AbstractTestNGSpringContextTests {
    @Inject
    private TeacherDao teacherDao;

    @Test
    @Transactional
    public void testCreateTeacher() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("Jožko");
        teacher.setLastName("Pročko");
        teacher.setUsername("JozkoProcko");
        teacher.setHashedPassword("passHash");

        Assert.assertEquals(teacherDao.findAll().size(), 0);

        teacherDao.create(teacher);

        Assert.assertEquals(teacherDao.findAll().size(), 1);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    @Transactional
    public void testCreateTeacherNullFirstName() {
        Teacher teacher = new Teacher();
        teacher.setLastName("Pročko");
        teacher.setUsername("JozkoProcko");
        teacher.setHashedPassword("passHash");

        teacherDao.create(teacher);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    @Transactional
    public void testCreateTeacherNullLastName() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("Jožko");
        teacher.setUsername("JozkoProcko");
        teacher.setHashedPassword("passHash");

        teacherDao.create(teacher);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    @Transactional
    public void testCreateTeacherNullUsername() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("Jožko");
        teacher.setLastName("Pročko");
        teacher.setHashedPassword("passHash");

        teacherDao.create(teacher);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    @Transactional
    public void testCreateTeacherNullHashedPassword() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("Jožko");
        teacher.setLastName("Pročko");
        teacher.setUsername("JozkoProcko");

        teacherDao.create(teacher);
    }

    @Test
    @Transactional
    public void testFindAllTeachers() {
        Teacher teacher1 = new Teacher();
        teacher1.setFirstName("Jožko");
        teacher1.setLastName("Pročko");
        teacher1.setUsername("JozkoProcko");
        teacher1.setHashedPassword("passHash1");

        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("Alena");
        teacher2.setLastName("Kováčova");
        teacher2.setUsername("AlenaKovacova");
        teacher2.setHashedPassword("passHash2");

        Assert.assertEquals(teacherDao.findAll().size(), 0);

        teacherDao.create(teacher1);
        teacherDao.create(teacher2);

        Assert.assertEquals(teacherDao.findAll().size(), 2);
    }

    @Test
    @Transactional
    public void testFindTeacherByExistingId () {
        Teacher teacher1 = new Teacher();
        teacher1.setFirstName("Jožko");
        teacher1.setLastName("Pročko");
        teacher1.setUsername("JozkoProcko");
        teacher1.setHashedPassword("passHash1");

        teacherDao.create(teacher1);

        Optional<Teacher> find = teacherDao.findById(teacher1.getId());

        Assert.assertTrue(find.isPresent());
        Assert.assertEquals(find.get().getFirstName(), "Jožko");
        Assert.assertEquals(find.get().getLastName(), "Pročko");
        Assert.assertEquals(find.get().getUsername(), "JozkoProcko");
        Assert.assertEquals(find.get().getHashedPassword(), "passHash1");
    }

    @Test
    @Transactional
    public void testFindTeacherByNonExistingId () {
        Optional<Teacher> find = teacherDao.findById(1L);
        Assert.assertFalse(find.isPresent());
    }

    @Test
    @Transactional
    public void testFindTeacherByExistingUsername () {
        Teacher teacher1 = new Teacher();
        teacher1.setFirstName("Jožko");
        teacher1.setLastName("Pročko");
        teacher1.setUsername("JozkoProcko");
        teacher1.setHashedPassword("passHash1");

        teacherDao.create(teacher1);

        Optional<Teacher> find = teacherDao.findByUsername("JozkoProcko");

        Assert.assertTrue(find.isPresent());
        Assert.assertEquals(find.get().getFirstName(), "Jožko");
        Assert.assertEquals(find.get().getLastName(), "Pročko");
        Assert.assertEquals(find.get().getUsername(), "JozkoProcko");
        Assert.assertEquals(find.get().getHashedPassword(), "passHash1");
    }

    @Test
    @Transactional
    public void testFindTeacherByNonExistingUsername () {
        Optional<Teacher> find = teacherDao.findByUsername("JankoKral");
        Assert.assertFalse(find.isPresent());
    }

    @Test
    @Transactional
    public void testUpdateTeacher () {
        Teacher teacher1 = new Teacher();
        teacher1.setFirstName("Jožko");
        teacher1.setLastName("Pročko");
        teacher1.setUsername("JozkoProcko");
        teacher1.setHashedPassword("passHash1");

        teacherDao.create(teacher1);

        teacher1.setFirstName("Jozef");
        teacher1.setLastName("Kolo");
        teacher1.setUsername("JozefKolo");
        teacher1.setHashedPassword("newPassHash1");

        teacherDao.update(teacher1);

        Optional<Teacher> find = teacherDao.findById(teacher1.getId());
        Assert.assertTrue(find.isPresent());
        Assert.assertEquals(find.get().getFirstName(), "Jozef");
        Assert.assertEquals(find.get().getLastName(), "Kolo");
        Assert.assertEquals(find.get().getUsername(), "JozefKolo");
        Assert.assertEquals(find.get().getHashedPassword(), "newPassHash1");
    }

    @Test
    @Transactional
    public void testRemoveTeacher () {
        Teacher teacher1 = new Teacher();
        teacher1.setFirstName("Jožko");
        teacher1.setLastName("Pročko");
        teacher1.setUsername("JozkoProcko");
        teacher1.setHashedPassword("passHash1");

        teacherDao.create(teacher1);

        Assert.assertEquals(teacherDao.findAll().size(), 1);

        teacherDao.remove(teacher1);

        Assert.assertEquals(teacherDao.findAll().size(), 0);
    }
}
