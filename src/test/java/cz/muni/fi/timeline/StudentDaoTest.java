package cz.muni.fi.timeline;

import cz.muni.fi.timeline.dao.StudentDao;
import cz.muni.fi.timeline.entity.Student;
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

/**
 * Tests for StudentDao.
 *
 * @author Karolína Veselá
 */
@ContextConfiguration(classes = HistoricalTimelineApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class StudentDaoTest extends AbstractTestNGSpringContextTests {
    @Inject
    private StudentDao studentDao;

    @Test
    @Transactional
    public void testCreateStudent() {
        Student student = new Student();
        student.setFirstName("Thrall");
        student.setLastName("Hellscream");
        student.setUsername("Warchief");
        student.setHashedPassword("1234");

        Assert.assertEquals(studentDao.findAll().size(), 0);

        studentDao.create(student);

        Assert.assertEquals(studentDao.findAll().size(), 1);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    @Transactional
    public void testCreateStudentNullFirstName() {
        Student student = new Student();
        student.setLastName("Hellscream");
        student.setUsername("Warchief");
        student.setHashedPassword("1234");

        studentDao.create(student);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    @Transactional
    public void testCreateStudentNullLastName() {
        Student student = new Student();
        student.setFirstName("Thrall");
        student.setUsername("Warchief");
        student.setHashedPassword("1234");

        studentDao.create(student);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    @Transactional
    public void testCreateStudentNullUsername() {
        Student student = new Student();
        student.setFirstName("Thrall");
        student.setLastName("Hellscream");
        student.setHashedPassword("1234");

        studentDao.create(student);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    @Transactional
    public void testCreateStudentNullHashedPassword() {
        Student student = new Student();
        student.setFirstName("Thrall");
        student.setLastName("Hellscream");
        student.setUsername("Warchief");

        studentDao.create(student);
    }
    @Test
    @Transactional
    public void testFindAllStudents() {
        Student student = new Student();
        student.setFirstName("Thrall");
        student.setLastName("Hellscream");
        student.setUsername("Warchief");
        student.setHashedPassword("1234");

        Student student2 = new Student();
        student2.setFirstName("David");
        student2.setLastName("Lister");
        student2.setUsername("DavidLister");
        student2.setHashedPassword("1234");

        Assert.assertEquals(studentDao.findAll().size(), 0);

        studentDao.create(student);
        studentDao.create(student2);

        Assert.assertEquals(studentDao.findAll().size(), 2);
    }
    @Test
    @Transactional
    public void testFindStudentById () {
        Student student1 = new Student();
        student1.setFirstName("Thrall");
        student1.setLastName("Hellscream");
        student1.setUsername("Warchief");
        student1.setHashedPassword("1234");

        studentDao.create(student1);

        Optional<Student> find = studentDao.findById(student1.getId());

        Assert.assertTrue(find.isPresent());
        Assert.assertEquals(find.get().getFirstName(), "Thrall");
        Assert.assertEquals(find.get().getLastName(), "Hellscream");
        Assert.assertEquals(find.get().getUsername(), "Warchief");
        Assert.assertEquals(find.get().getHashedPassword(), "1234");
    }



    @Test
    @Transactional
    public void testFindStudentByUsername () {
        Student student1 = new Student();
        student1.setFirstName("Thrall");
        student1.setLastName("Hellscream");
        student1.setUsername("Warchief");
        student1.setHashedPassword("1234");

        studentDao.create(student1);

        Optional<Student> find = studentDao.findByUserName("Warchief");

        Assert.assertTrue(find.isPresent());
        Assert.assertEquals(find.get().getFirstName(), "Thrall");
        Assert.assertEquals(find.get().getLastName(), "Hellscream");
        Assert.assertEquals(find.get().getUsername(), "Warchief");
        Assert.assertEquals(find.get().getHashedPassword(), "1234");
    }
    @Test
    @Transactional
    public void testUpdateStudent () {
        Student student1 = new Student();
        student1.setFirstName("Thrall");
        student1.setLastName("Hellscream");
        student1.setUsername("Warchief");
        student1.setHashedPassword("1234");

        studentDao.create(student1);

        student1.setFirstName("David");
        student1.setLastName("Lister");
        student1.setUsername("DavidLister");
        student1.setHashedPassword("4321");

        studentDao.update(student1);

        Optional<Student> find = studentDao.findById(student1.getId());
        Assert.assertTrue(find.isPresent());
        Assert.assertEquals(find.get().getFirstName(), "David");
        Assert.assertEquals(find.get().getLastName(), "Lister");
        Assert.assertEquals(find.get().getUsername(), "DavidLister");
        Assert.assertEquals(find.get().getHashedPassword(), "4321");
    }

    @Test
    @Transactional
    public void testRemoveStudent () {
        Student student1 = new Student();
        student1.setFirstName("Thrall");
        student1.setLastName("Hellscream");
        student1.setUsername("Warchief");
        student1.setHashedPassword("1234");

        studentDao.create(student1);

        Assert.assertEquals(studentDao.findAll().size(), 1);

        studentDao.remove(student1);

        Assert.assertEquals(studentDao.findAll().size(), 0);
    }
}

