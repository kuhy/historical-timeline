package cz.muni.fi.timeline.service;

import cz.muni.fi.timeline.dao.UserDao;
import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.service.exception.ServiceLayerException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.*;

import static org.mockito.Mockito.*;


/**
 * Tests for user service
 *
 * @author Tri Le Mau
 */
public class UserServiceTest {
    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder encoder;

    @Autowired
    private UserService userService;

    private AutoCloseable autoCloseable;

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
        teacher1.setId(1L);
        teacher1.setFirstName("Thrall");
        teacher1.setLastName("Hellscream");
        teacher1.setUsername("Warchief");
        teacher1.setHashedPassword("1234");
        teacher1.setIsTeacher(true);
        teacher1.addStudyGroup(englishHistoryGroup);
        teacher1.addStudyGroup(asianHistoryGroup);

        teacher2 = new User();
        teacher2.setId(2L);
        teacher2.setFirstName("Jo??ko");
        teacher2.setLastName("Pro??ko");
        teacher2.setUsername("JozkoProcko");
        teacher2.setHashedPassword("passHash1");
        teacher2.setIsTeacher(true);
        teacher2.addStudyGroup(asianHistoryGroup);

        student1 = new User();
        student1.setId(3L);
        student1.setFirstName("David");
        student1.setLastName("Lister");
        student1.setUsername("DavidLister");
        student1.setHashedPassword("1234");
        student1.setIsTeacher(false);
        student1.addStudyGroup(englishHistoryGroup);
        student1.addStudyGroup(asianHistoryGroup);

        student2 = new User();
        student2.setId(4L);
        student2.setFirstName("Alena");
        student2.setLastName("Kov????ova");
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

    @BeforeMethod
    public void openMocks() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userDao, encoder);
    }

    @AfterMethod
    public void releaseMocks() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void testRegisterUser() {
        when(encoder.encode(anyString())).thenReturn("hashedPassword");
        userService.registerUser(student1, "encryptedPassword");

        verify(userDao, times(1)).create(any(User.class));
        verifyNoMoreInteractions(userDao);

        verify(encoder, times(1)).encode(anyString());
        verifyNoMoreInteractions(encoder);
    }

    @Test
    public void testLoginUserAndLogout() {
        when(userDao.findByUserName(student1.getUsername())).thenReturn(Optional.ofNullable(student1));

        Assert.assertEquals(userService.getLoggedInUser(), Optional.empty());
        Assert.assertFalse(userService.isUserLoggedIn());

        // Login
        when(encoder.matches(anyString(), anyString())).thenReturn(true);
        Assert.assertTrue(userService.loginUser(student1, "hashedPassword"));

        Optional<User> find = userService.getLoggedInUser();
        Assert.assertTrue(find.isPresent());
        Assert.assertEquals(find.get(), student1);
        Assert.assertTrue(userService.isUserLoggedIn());

        verify(userDao, times(1)).findByUserName(student1.getUsername());
        verify(encoder, times(1)).matches(anyString(), anyString());
        verifyNoMoreInteractions(userDao);
        verifyNoMoreInteractions(encoder);

        // Logout
        userService.logoutUser();
        Assert.assertEquals(userService.getLoggedInUser(), Optional.empty());
        Assert.assertFalse(userService.isUserLoggedIn());

        verifyNoMoreInteractions(userDao);
        verifyNoMoreInteractions(encoder);
    }

    @Test
    public void testUpdateUser() {
        userService.updateUser(student1);

        verify(userDao, times(1)).update(any(User.class));
        verifyNoMoreInteractions(userDao);

        verifyNoInteractions(encoder);
    }

    @Test
    public void testRemoveUser() {
        userService.removeUser(student1);

        verify(userDao, times(1)).remove(any(User.class));
        verifyNoMoreInteractions(userDao);

        verifyNoInteractions(encoder);
    }

    @Test
    public void testFindById() {
        when(userDao.findById(student1.getId())).thenReturn(Optional.ofNullable(student1));
        Optional<User> find = userService.findById(student1.getId());

        Assert.assertTrue(find.isPresent());
        Assert.assertEquals(find.get(), student1);

        verify(userDao, times(1)).findById(anyLong());
        verifyNoMoreInteractions(userDao);

        verifyNoInteractions(encoder);
    }

    @Test
    public void testFindByUsername() {
        when(userDao.findByUserName(student1.getUsername())).thenReturn(Optional.ofNullable(student1));
        Optional<User> find = userService.findByUsername(student1.getUsername());

        Assert.assertTrue(find.isPresent());
        Assert.assertEquals(find.get(), student1);

        verify(userDao, times(1)).findByUserName(anyString());
        verifyNoMoreInteractions(userDao);

        verifyNoInteractions(encoder);
    }

    @Test
    public void testExistingUserIsTeacher() {
        when(userDao.findById(teacher1.getId())).thenReturn(Optional.ofNullable(teacher1));
        Assert.assertTrue(userService.isTeacher(teacher1));

        verify(userDao, times(1)).findById(anyLong());
        verifyNoMoreInteractions(userDao);

        verifyNoInteractions(encoder);
    }

    @Test(expectedExceptions = ServiceLayerException.class)
    public void testNonExistingUserIsTeacher() {
        when(userDao.findById(teacher1.getId())).thenReturn(Optional.empty());
        Assert.assertTrue(userService.isTeacher(teacher1));

        verify(userDao, times(1)).findById(anyLong());
        verifyNoMoreInteractions(userDao);

        verifyNoInteractions(encoder);
    }

    @Test
    public void testGetAllUsers() {
        when(userDao.findAll()).thenReturn(users);
        Assert.assertEquals(userService.getAllUsers(), users);

        verify(userDao, times(1)).findAll();
        verifyNoMoreInteractions(userDao);

        verifyNoInteractions(encoder);
    }

    @Test
    public void testGetAllStudents() {
        when(userDao.findAllStudents()).thenReturn(students);
        Assert.assertEquals(userService.getAllStudents(), students);

        verify(userDao, times(1)).findAllStudents();
        verifyNoMoreInteractions(userDao);

        verifyNoInteractions(encoder);
    }

    @Test
    public void testGetAllTeacher() {
        when(userDao.findAllTeachers()).thenReturn(teachers);
        Assert.assertEquals(userService.getAllTeachers(), teachers);

        verify(userDao, times(1)).findAllTeachers();
        verifyNoMoreInteractions(userDao);

        verifyNoInteractions(encoder);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRegisterUserNullUser() {
        userService.registerUser(null, "password");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRegisterUserNullPassword() {
        userService.registerUser(teacher1, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testLoginuserUserNullUser() {
        userService.loginUser(null, "passowrd");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testLoginuserUserNullPassword() {
        userService.loginUser(teacher1, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateUserNull() {
        userService.updateUser(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRemoveUserNull() {
        userService.removeUser(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByIdNull() {
        userService.findById(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFindByUserNameNull() {
        userService.findByUsername(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testIsTeacherNull() {
        userService.isTeacher(null);
    }
}
