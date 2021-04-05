package cz.muni.fi.timeline;

import cz.muni.fi.timeline.dao.HistoricalTimelineDao;
import cz.muni.fi.timeline.entity.HistoricalTimeline;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@ContextConfiguration(classes = HistoricalTimelineApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class HistoricalTimelineDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private HistoricalTimelineDao historicalTimelineDao;

    @Test
    @Transactional
    public void testCreateHistoricalTimeline(){
        HistoricalTimeline romanEmpire = new HistoricalTimeline();
        romanEmpire.setName("Roman Empire");

        Assert.assertEquals(historicalTimelineDao.FindAll().size(),0);

        historicalTimelineDao.create(romanEmpire);

        Assert.assertEquals(historicalTimelineDao.FindAll().size(),1);
    }

    @Test
    @Transactional
    public void testMoreHistoricalTimeline(){
        HistoricalTimeline romanEmpire = new HistoricalTimeline();
        romanEmpire.setName("Roman Empire");
        historicalTimelineDao.create(romanEmpire);

        HistoricalTimeline ancientGreece = new HistoricalTimeline();
        ancientGreece.setName("Ancient Greece");
        historicalTimelineDao.create(ancientGreece);

        HistoricalTimeline persianEmpire = new HistoricalTimeline();
        persianEmpire.setName("Persian Empire");
        historicalTimelineDao.create(persianEmpire);

        Assert.assertEquals(historicalTimelineDao.FindAll().size(),3);
    }

    @Test
    @Transactional
    public void testFindHistoricalTimelineByName(){
        HistoricalTimeline romanEmpire = new HistoricalTimeline();
        romanEmpire.setName("After Alexander");
        historicalTimelineDao.create(romanEmpire);

        HistoricalTimeline ancientGreece = new HistoricalTimeline();
        ancientGreece.setName("Defeated by Alexander");
        historicalTimelineDao.create(ancientGreece);

        HistoricalTimeline persianEmpire = new HistoricalTimeline();
        persianEmpire.setName("Defeated by Alexander");
        historicalTimelineDao.create(persianEmpire);

        HistoricalTimeline ancientIndia = new HistoricalTimeline();
        ancientIndia.setName("Defeated by Alexander");
        historicalTimelineDao.create(ancientIndia);

        List<HistoricalTimeline> defeatedByAlexander = historicalTimelineDao.findByName("Defeated by Alexander");
        List<HistoricalTimeline> undefeatedByAlexander = historicalTimelineDao.findByName("After Alexander");


        Assert.assertEquals(defeatedByAlexander.size(), 3);
        Assert.assertEquals(defeatedByAlexander.get(0).getName(), "Defeated by Alexander");
        Assert.assertEquals(defeatedByAlexander.get(1).getName(), "Defeated by Alexander");

        Assert.assertEquals(undefeatedByAlexander.size(), 1);
        Assert.assertEquals(undefeatedByAlexander.get(0).getName(), "After Alexander");
    }

    @Test
    @Transactional
    public void testRemoveHistoricalTimeline(){
        HistoricalTimeline romanEmpire = new HistoricalTimeline();
        romanEmpire.setName("Roman Empire");
        historicalTimelineDao.create(romanEmpire);

        Assert.assertEquals(historicalTimelineDao.FindAll().size(),1);

        historicalTimelineDao.remove(romanEmpire);

        Assert.assertEquals(historicalTimelineDao.FindAll().size(),0);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    @Transactional
    public void testForNotNullException(){
        HistoricalTimeline romanEmpire = new HistoricalTimeline();

        historicalTimelineDao.create(romanEmpire);
    }

    @Test
    @Transactional
    public void testFindHistoricalTimelineByID(){
        HistoricalTimeline romanEmpire = new HistoricalTimeline();
        romanEmpire.setName("Roman Empire");
        historicalTimelineDao.create(romanEmpire);

        Optional<HistoricalTimeline> timeline = historicalTimelineDao.findById(romanEmpire.getId());

        Assert.assertTrue(timeline.isPresent());
        Assert.assertEquals(timeline.get().getName(),"Roman Empire");
        Assert.assertFalse(historicalTimelineDao.findById(romanEmpire.getId() + 10).isPresent());
    }
}
