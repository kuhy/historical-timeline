package cz.muni.fi.timeline;

import cz.muni.fi.timeline.dao.StudyGroupDao;
import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.service.UserAlreadyInStudyGroupException;
import cz.muni.fi.timeline.service.StudyGroupService;
import cz.muni.fi.timeline.service.StudyGroupServiceImpl;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests for service of study groups
 * @author Matej Macák
 */
public class StudyGroupServiceTest{

    @Mock
    private StudyGroupDao studyGroupDao;

    private StudyGroupService studyGroupService;

    private AutoCloseable closeable;

    // Study groups
    private StudyGroup group1;
    private StudyGroup group2;

    private User student1;
    private User student2;
    private User student3;
    private User teacher1;

    @BeforeMethod
    public void prepareStudyGroups() {
        group1 = new StudyGroup();
        group1.setName("PA165/01");
        group1.setId(1L);

        group2 = new StudyGroup();
        group2.setName("PA165/02");
        group2.setId(2L);
    }

    @BeforeMethod
    public void prepareUsers() {

        teacher1 = new User();
        teacher1.setFirstName("Jožko");
        teacher1.setLastName("Pročko");
        teacher1.setUsername("JozkoProcko");
        teacher1.setHashedPassword("passHash1");
        teacher1.setIsTeacher(true);
        teacher1.addStudyGroup(group2);

        student1 = new User();
        student1.setFirstName("David");
        student1.setLastName("Lister");
        student1.setUsername("DavidLister");
        student1.setHashedPassword("1234");
        student1.setIsTeacher(false);
        student1.addStudyGroup(group2);

        student2 = new User();
        student2.setFirstName("Alena");
        student2.setLastName("Kováčova");
        student2.setUsername("AlenaKovacova");
        student2.setHashedPassword("passHash2");
        student2.setIsTeacher(false);
        student2.addStudyGroup(group2);

    }

    @BeforeMethod
    public void prepareService() {
        studyGroupService = new StudyGroupServiceImpl(studyGroupDao) {
        };
    }

    @BeforeMethod
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
        studyGroupService = new StudyGroupServiceImpl(studyGroupDao);
    }

    @AfterMethod
    public void releaseMocks() throws Exception {
        closeable.close();
    }


        @Test
    public void testCreateStudyGroup() {
        studyGroupService.createStudyGroup(group1);
        verify(studyGroupDao, times(1)).create(any(StudyGroup.class));
        Mockito.verifyNoMoreInteractions(studyGroupDao);

            verifyNoMoreInteractions(studyGroupDao);
    }

    @Test
    public void testUpdateStudyGroup() {
        studyGroupService.updateStudyGroup(group1);
        verify(studyGroupDao, times(1)).update(any(StudyGroup.class));

        verifyNoMoreInteractions(studyGroupDao);
    }

    @Test
    public void testRemoveStudyGroup() {
        studyGroupService.removeStudyGroup(group1);
        verify(studyGroupDao, times(1)).remove(any(StudyGroup.class));

        verifyNoMoreInteractions(studyGroupDao);

    }

    @Test
    public void testAddUserToStudyGroup() throws UserAlreadyInStudyGroupException {
        when(studyGroupDao.findById(group2.getId())).thenReturn(Optional.of(group2));

        Assert.assertEquals(group2.getUsers().size(),0);
        studyGroupService.addUserToStudyGroup(group2,teacher1);
        studyGroupService.addUserToStudyGroup(group2,student1);
        studyGroupService.addUserToStudyGroup(group2,student2);
        Assert.assertEquals(group2.getUsers().size(),3);

        verify(studyGroupDao, times(3)).update(any(StudyGroup.class));
        verify(studyGroupDao,times(3)).findById(2L);
        verifyNoMoreInteractions(studyGroupDao);

    }

    @Test
    public void testFindById() {
        when(studyGroupDao.findById(group2.getId())).thenReturn(Optional.ofNullable(group2));
        Optional<StudyGroup> found = studyGroupService.findById(group2.getId());

        Assert.assertTrue(found.isPresent());
        Assert.assertEquals(found.get(), group2);

        Mockito.verify(studyGroupDao).findById(group2.getId());
        Mockito.verifyNoMoreInteractions(studyGroupDao);

        verifyNoMoreInteractions(studyGroupDao);

    }

    @Test
    public void testFindAllStudyGroups(){
        when(studyGroupDao.findAll()).thenReturn(List.of(group1, group2));

        List<StudyGroup> timelines = studyGroupService.findAllStudyGroups();

        Assert.assertEquals(timelines.size(), 2);
        Assert.assertTrue(timelines.contains(group1));
        Assert.assertTrue(timelines.contains(group2));

        Mockito.verify(studyGroupDao).findAll();
        Mockito.verifyNoMoreInteractions(studyGroupDao);

        verifyNoMoreInteractions(studyGroupDao);

    }

}
