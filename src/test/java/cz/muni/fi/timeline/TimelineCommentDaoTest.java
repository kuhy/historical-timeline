package cz.muni.fi.timeline;

import cz.muni.fi.timeline.dao.TimelineCommentDao;
import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.entity.TimelineComment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * tests for timelineComment DAO
 * @author Matej Macák
 */
@ContextConfiguration(classes = HistoricalTimelineApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class TimelineCommentDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private TimelineCommentDao timelineCommentDao;

    @Test
    @Transactional
    public void testCreateTimelineComment(){
        TimelineComment positiveComment = new TimelineComment();
        positiveComment.setText("Very interesting");

        Assert.assertEquals(timelineCommentDao.findAll().size(),0);

        timelineCommentDao.create(positiveComment);

        Assert.assertEquals(timelineCommentDao.findAll().size(),1);
    }

    @Test
    @Transactional
    public void testMoreTimelineComments(){
        TimelineComment positiveComment = new TimelineComment();
        positiveComment.setText("Very interesting");
        timelineCommentDao.create(positiveComment);

        TimelineComment questionComment = new TimelineComment();
        questionComment.setText("How old was the Alexander when invading India?");
        timelineCommentDao.create(questionComment);

        Assert.assertEquals(timelineCommentDao.findAll().size(),2);
    }

    @Test
    @Transactional
    public void testRemoveTimelineComment(){
        TimelineComment positiveComment = new TimelineComment();
        positiveComment.setText("Very interesting");
        timelineCommentDao.create(positiveComment);

        Assert.assertEquals(timelineCommentDao.findAll().size(),1);

        timelineCommentDao.remove(positiveComment);

        Assert.assertEquals(timelineCommentDao.findAll().size(),0);
    }

    @Test
    @Transactional
    public void testFindTimelineCommentByID(){
        TimelineComment questionComment = new TimelineComment();
        questionComment.setText("How old was the Alexander when invading India?");
        timelineCommentDao.create(questionComment);

        Optional<TimelineComment> comment = timelineCommentDao.findById(questionComment.getId());

        Assert.assertTrue(comment.isPresent());
        Assert.assertEquals(comment.get().getText(),"How old was the Alexander when invading India?");
        Assert.assertFalse(timelineCommentDao.findById(questionComment.getId() + 10).isPresent());
    }

    @Test(expectedExceptions = PersistenceException.class)
    @Transactional
    public void testForNotNullException(){
        TimelineComment negativeComment = new TimelineComment();

        timelineCommentDao.create(negativeComment);
        timelineCommentDao.findAll();
    }

    @Test
    @Transactional
    public void testFindTimelineCommentByIDWithStudent(){
        TimelineComment studentComment = new TimelineComment();
        User joseph = new User();
        joseph.setFirstName("Joseph");
        joseph.setLastName("Klein");
        joseph.setUsername("xklein");
        joseph.setHashedPassword("123456789");
        studentComment.setUser(joseph);
        studentComment.setText("May I have a question?");
        timelineCommentDao.create(studentComment);

        Optional<TimelineComment> comment = timelineCommentDao.findById(studentComment.getId());

        Assert.assertTrue(comment.isPresent());
        Assert.assertEquals(comment.get().getUser().getHashedPassword(),"123456789");
        Assert.assertEquals(comment.get().getUser().getUsername(),"xklein");
        Assert.assertEquals(comment.get().getUser().getLastName(),"Klein");
        Assert.assertEquals(comment.get().getUser().getFirstName(),"Joseph");
    }

    @Test
    @Transactional
    public void testUpdateTimelineComment(){
        TimelineComment meanCommentToPositive = new TimelineComment();
        meanCommentToPositive.setText("This is so boring");
        timelineCommentDao.create(meanCommentToPositive);

        Optional<TimelineComment> comment = timelineCommentDao.findById(meanCommentToPositive.getId());

        meanCommentToPositive.setText("Changed to positive");
        timelineCommentDao.update(meanCommentToPositive);

        Assert.assertEquals(comment.get().getText(),"Changed to positive");
    }
}
