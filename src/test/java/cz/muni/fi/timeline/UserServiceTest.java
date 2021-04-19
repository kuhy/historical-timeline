package cz.muni.fi.timeline;

import cz.muni.fi.timeline.dao.UserDao;
import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = HistoricalTimelineApplicationContext.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder encoder;

    @Autowired
    @InjectMocks
    private UserService userService;

    // Study groups
    private StudyGroup englishHistoryGroup;
    private StudyGroup asianHistoryGroup;

    // Users
    private User student1;
    private User student2;
    private User teacher1;
    private User teacher2;
    private List<User> users;
    private List<User> students;
    private List<User> teachers;

    @BeforeMethod
    public void prepareStudyGroups() {
        englishHistoryGroup = new StudyGroup();
        englishHistoryGroup.setName("English history group");

        asianHistoryGroup = new StudyGroup();
        asianHistoryGroup.setName("Asian history group");
    }

    @BeforeMethod
    public void prepareUsers() {
        teacher1 = new User();
        teacher1.setFirstName("Thrall");
        teacher1.setLastName("Hellscream");
        teacher1.setUsername("Warchief");
        teacher1.setHashedPassword("1234");
        teacher1.setIsTeacher(true);
        teacher1.addStudyGroup(englishHistoryGroup);
        teacher1.addStudyGroup(asianHistoryGroup);

        teacher2 = new User();
        teacher2.setFirstName("Jožko");
        teacher2.setLastName("Pročko");
        teacher2.setUsername("JozkoProcko");
        teacher2.setHashedPassword("passHash1");
        teacher2.setIsTeacher(true);
        teacher2.addStudyGroup(asianHistoryGroup);

        student1 = new User();
        student1.setFirstName("David");
        student1.setLastName("Lister");
        student1.setUsername("DavidLister");
        student1.setHashedPassword("1234");
        student1.setIsTeacher(false);
        student1.addStudyGroup(englishHistoryGroup);
        student1.addStudyGroup(asianHistoryGroup);

        student2 = new User();
        student2.setFirstName("Alena");
        student2.setLastName("Kováčova");
        student2.setUsername("AlenaKovacova");
        student2.setHashedPassword("passHash2");
        student2.setIsTeacher(false);
        student2.addStudyGroup(englishHistoryGroup);

        users = new ArrayList<>();
        users.add(student1);
        users.add(student2);
        users.add(teacher1);
        users.add(teacher2);

        students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        teachers = new ArrayList<>();
        teachers.add(teacher1);
        teachers.add(teacher2);
    }

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterUser() {
        when(encoder.encode(anyString())).thenReturn("hashedPassword");
        userService.registerUser(student1, "encryptedPassword");
        verify(userDao, times(1)).create(any(User.class));
    }

    @Test
    public void testAuthenticateUser() {
        when(encoder.matches(anyString(), anyString())).thenReturn(true);
        Assert.assertTrue(userService.authenticateUser(student1, "hashedPassword"));
    }

    @Test
    public void testGetAllUsers() {
        when(userDao.findAll()).thenReturn(users);
        Assert.assertEquals(userService.getAllUsers(), users);
    }

    @Test
    public void testGetAllStudents() {
        when(userDao.findAllStudents()).thenReturn(students);
        Assert.assertEquals(userService.getAllStudents(), students);
    }

    @Test
    public void testGetAllTeacher() {
        when(userDao.findAllTeachers()).thenReturn(teachers);
        Assert.assertEquals(userService.getAllTeachers(), teachers);
    }
}
