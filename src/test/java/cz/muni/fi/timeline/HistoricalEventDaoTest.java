package cz.muni.fi.timeline;

import cz.muni.fi.timeline.dao.HistoricalEventDao;
import cz.muni.fi.timeline.entity.HistoricalEvent;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 * Tests for HistoricalEventDao.
 *
 * @author Ondřej Kuhejda
 */
@ContextConfiguration(classes = HistoricalTimelineApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
public class HistoricalEventDaoTest extends AbstractTestNGSpringContextTests {

    @Inject
    private HistoricalEventDao historicalEventDao;

    @Test
    @Transactional
    public void testCreateHistoricalEvent() {
        HistoricalEvent nativityOfJesus = new HistoricalEvent();
        nativityOfJesus.setName("Nativity of Jesus");

        Assert.assertEquals(historicalEventDao.findAll().size(), 0);

        historicalEventDao.create(nativityOfJesus);

        Assert.assertEquals(historicalEventDao.findAll().size(), 1);
    }

    @Test
    @Transactional
    public void testFindAllHistoricalEvents() {
        HistoricalEvent nativityOfJesus = new HistoricalEvent();
        nativityOfJesus.setName("Nativity of Jesus");
        historicalEventDao.create(nativityOfJesus);

        HistoricalEvent crucifixionOfJesus = new HistoricalEvent();
        crucifixionOfJesus.setName("Crucifixion of Jesus");
        historicalEventDao.create(crucifixionOfJesus);

        Assert.assertEquals(historicalEventDao.findAll().size(), 2);
    }

    @Test
    @Transactional
    public void testFindHistoricalEventById() {
        HistoricalEvent nativityOfJesus = new HistoricalEvent();
        nativityOfJesus.setName("Nativity of Jesus");
        historicalEventDao.create(nativityOfJesus);

        Optional<HistoricalEvent> event = historicalEventDao.findById(nativityOfJesus.getId());
        Assert.assertTrue(event.isPresent());
        Assert.assertEquals(event.get().getName(), "Nativity of Jesus");

        Assert.assertFalse(historicalEventDao.findById(nativityOfJesus.getId() + 1).isPresent());
    }

    @Test
    @Transactional
    public void testFindHistoricalEventByName() {
        HistoricalEvent funeralOfPope1 = new HistoricalEvent();
        funeralOfPope1.setName("Funeral of Pope");
        historicalEventDao.create(funeralOfPope1);

        HistoricalEvent funeralOfPope2 = new HistoricalEvent();
        funeralOfPope2.setName("Funeral of Pope");
        historicalEventDao.create(funeralOfPope2);

        HistoricalEvent crucifixionOfJesus = new HistoricalEvent();
        crucifixionOfJesus.setName("Crucifixion of Jesus");
        historicalEventDao.create(crucifixionOfJesus);

        List<HistoricalEvent> funeralsOfPopes = historicalEventDao.findByName("Funeral of Pope");
        Assert.assertEquals(funeralsOfPopes.size(), 2);
        Assert.assertEquals(funeralsOfPopes.get(0).getName(), "Funeral of Pope");
        Assert.assertEquals(funeralsOfPopes.get(1).getName(), "Funeral of Pope");
    }

    @Test
    @Transactional
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void testUpdateHistoricalEvent() {
        HistoricalEvent nativityOfJesus = new HistoricalEvent();
        nativityOfJesus.setName("Nativity of Jesus");
        historicalEventDao.create(nativityOfJesus);

        HistoricalEvent transientNativityOfJesus = new HistoricalEvent();
        transientNativityOfJesus.setId(nativityOfJesus.getId());
        transientNativityOfJesus.setName("Nativity of Jesus");
        transientNativityOfJesus.setDate(LocalDate.of(1, 12, 25));
        historicalEventDao.update(transientNativityOfJesus);

        nativityOfJesus = historicalEventDao.findById(nativityOfJesus.getId()).get();
        Assert.assertNotNull(nativityOfJesus.getDate());
    }

    @Test
    @Transactional
    public void testRemoveHistoricalEvent() {
        HistoricalEvent nativityOfJesus = new HistoricalEvent();
        nativityOfJesus.setName("Nativity of Jesus");

        historicalEventDao.create(nativityOfJesus);

        Assert.assertEquals(historicalEventDao.findAll().size(), 1);

        historicalEventDao.remove(nativityOfJesus);

        Assert.assertEquals(historicalEventDao.findAll().size(), 0);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    @Transactional
    public void testThatNameOfTheHistoricalEventCannotBeNull() {
        HistoricalEvent nativityOfJesus = new HistoricalEvent();

        historicalEventDao.create(nativityOfJesus);
    }
}
