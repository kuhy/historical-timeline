package cz.muni.fi.timeline;

import cz.muni.fi.timeline.dao.StudyGroupDao;
import cz.muni.fi.timeline.entity.StudyGroup;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.service.ContainsStudentException;
import cz.muni.fi.timeline.service.StudyGroupService;
import cz.muni.fi.timeline.service.StudyGroupServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ContextConfiguration(classes = HistoricalTimelineApplicationContext.class)
public class StudyGroupServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private StudyGroupDao studyGroupDao;

    @Autowired
    @InjectMocks
    private StudyGroupService studyGroupService;

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
        Long id_1 = new Long(1);
        group1.setId(id_1);

        group2 = new StudyGroup();
        group2.setName("PA165/02");
        Long id_2 = new Long(2);
        group2.setId(id_2);
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


    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreateStudyGroup() {
        studyGroupService.createStudyGroup(group1);
        verify(studyGroupDao, times(1)).create(any(StudyGroup.class));
    }

    @Test
    public void testUpdateStudyGroup() {
        studyGroupService.updateStudyGroup(group1);
        verify(studyGroupDao, times(1)).update(group1);
    }

    @Test
    public void testRemoveStudyGroup() {
        studyGroupService.removeStudyGroup(group1);
        verify(studyGroupDao, times(1)).remove(any(StudyGroup.class));
    }

    @Test
    public void testAddUserToStudyGroup() throws ContainsStudentException {
        Assert.assertEquals(group2.getUsers().size(),0);
        studyGroupService.addUserToStudyGroup(group2,teacher1);
        studyGroupService.addUserToStudyGroup(group2,student1);
        studyGroupService.addUserToStudyGroup(group2,student2);
        Assert.assertEquals(group2.getUsers().size(),3);
    }

    @Test
    public void testFindById() {
        when(studyGroupDao.findById(group2.getId())).thenReturn(Optional.ofNullable(group2));
        Optional<StudyGroup> found = studyGroupService.findById(group2.getId());

        Assert.assertTrue(found.isPresent());
        Assert.assertEquals(found.get(), group2);
    }
}
