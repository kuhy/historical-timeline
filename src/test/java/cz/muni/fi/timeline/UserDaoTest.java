package cz.muni.fi.timeline;

import cz.muni.fi.timeline.dao.UserDao;
import cz.muni.fi.timeline.entity.User;
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
 * Tests for UserDao.
 *
 * @author Karolína Veselá
 * TODO test if the user is a teacher
 */
@ContextConfiguration(classes = HistoricalTimelineApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class UserDaoTest extends AbstractTestNGSpringContextTests {
    @Inject
    private UserDao userDao;

    @Test
    @Transactional
    public void testCreateUser() {
        User user = new User();
        user.setFirstName("Thrall");
        user.setLastName("Hellscream");
        user.setUsername("Warchief");
        user.setHashedPassword("1234");

        Assert.assertEquals(userDao.findAll().size(), 0);

        userDao.create(user);

        Assert.assertEquals(userDao.findAll().size(), 1);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    @Transactional
    public void testCreateUserNullFirstName() {
        User user = new User();
        user.setLastName("Hellscream");
        user.setUsername("Warchief");
        user.setHashedPassword("1234");

        userDao.create(user);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    @Transactional
    public void testCreateUserNullLastName() {
        User user = new User();
        user.setFirstName("Thrall");
        user.setUsername("Warchief");
        user.setHashedPassword("1234");

        userDao.create(user);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    @Transactional
    public void testCreateUserNullUsername() {
        User user = new User();
        user.setFirstName("Thrall");
        user.setLastName("Hellscream");
        user.setHashedPassword("1234");

        userDao.create(user);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    @Transactional
    public void testCreateUserNullHashedPassword() {
        User user = new User();
        user.setFirstName("Thrall");
        user.setLastName("Hellscream");
        user.setUsername("Warchief");

        userDao.create(user);
    }
    @Test
    @Transactional
    public void testFindAllUsers() {
        User user = new User();
        user.setFirstName("Thrall");
        user.setLastName("Hellscream");
        user.setUsername("Warchief");
        user.setHashedPassword("1234");

        User user2 = new User();
        user2.setFirstName("David");
        user2.setLastName("Lister");
        user2.setUsername("DavidLister");
        user2.setHashedPassword("1234");

        Assert.assertEquals(userDao.findAll().size(), 0);

        userDao.create(user);
        userDao.create(user2);

        Assert.assertEquals(userDao.findAll().size(), 2);
    }
    @Test
    @Transactional
    public void testFindUserById () {
        User user1 = new User();
        user1.setFirstName("Thrall");
        user1.setLastName("Hellscream");
        user1.setUsername("Warchief");
        user1.setHashedPassword("1234");

        userDao.create(user1);

        Optional<User> find = userDao.findById(user1.getId());

        Assert.assertTrue(find.isPresent());
        Assert.assertEquals(find.get().getFirstName(), "Thrall");
        Assert.assertEquals(find.get().getLastName(), "Hellscream");
        Assert.assertEquals(find.get().getUsername(), "Warchief");
        Assert.assertEquals(find.get().getHashedPassword(), "1234");
    }



    @Test
    @Transactional
    public void testFindUserByUsername () {
        User user1 = new User();
        user1.setFirstName("Thrall");
        user1.setLastName("Hellscream");
        user1.setUsername("Warchief");
        user1.setHashedPassword("1234");

        userDao.create(user1);

        Optional<User> find = userDao.findByUserName("Warchief");

        Assert.assertTrue(find.isPresent());
        Assert.assertEquals(find.get().getFirstName(), "Thrall");
        Assert.assertEquals(find.get().getLastName(), "Hellscream");
        Assert.assertEquals(find.get().getUsername(), "Warchief");
        Assert.assertEquals(find.get().getHashedPassword(), "1234");
    }
    @Test
    @Transactional
    public void testUpdateUser () {
        User user1 = new User();
        user1.setFirstName("Thrall");
        user1.setLastName("Hellscream");
        user1.setUsername("Warchief");
        user1.setHashedPassword("1234");

        userDao.create(user1);

        user1.setFirstName("David");
        user1.setLastName("Lister");
        user1.setUsername("DavidLister");
        user1.setHashedPassword("4321");

        userDao.update(user1);

        Optional<User> find = userDao.findById(user1.getId());
        Assert.assertTrue(find.isPresent());
        Assert.assertEquals(find.get().getFirstName(), "David");
        Assert.assertEquals(find.get().getLastName(), "Lister");
        Assert.assertEquals(find.get().getUsername(), "DavidLister");
        Assert.assertEquals(find.get().getHashedPassword(), "4321");
    }

    @Test
    @Transactional
    public void testRemoveUser () {
        User user1 = new User();
        user1.setFirstName("Thrall");
        user1.setLastName("Hellscream");
        user1.setUsername("Warchief");
        user1.setHashedPassword("1234");

        userDao.create(user1);

        Assert.assertEquals(userDao.findAll().size(), 1);

        userDao.remove(user1);

        Assert.assertEquals(userDao.findAll().size(), 0);
    }
}

