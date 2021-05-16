package cz.muni.fi.timeline.data;

import cz.muni.fi.timeline.entity.*;
import cz.muni.fi.timeline.service.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDate;


/**
 * Implementation of TimelineDataLoader.
 *
 * @author Ondřej Kuhejda
 */
@Component
@Transactional
public class TimelineDataLoaderImpl implements TimelineDataLoader {

    private final UserService userService;

    private final StudyGroupService groupService;

    private final HistoricalTimelineService timelineService;

    private final TimelineCommentService commentService;

    private final HistoricalEventService eventService;

    @Inject
    public TimelineDataLoaderImpl(UserService userService, StudyGroupService groupService, HistoricalTimelineService timelineService, TimelineCommentService commentService, HistoricalEventService eventService) {
        this.userService = userService;
        this.groupService = groupService;
        this.timelineService = timelineService;
        this.commentService = commentService;
        this.eventService = eventService;
    }

    @Override
    public void loadData() {
        // Users
        User user1 = createUser("Jan", "Komenský", "amos", true, "skakalzoknanazensky");
        User user2 = createUser("Ondřej", "Kuhejda", "kuhy", false, "hovnokleslo");
        User user3 = createUser("Jožko", "Pročko", "JozkoProcko", false, "passHash1");
        User user4 = createUser("Albín", "Kučera", "xkucera", false, "passHash2");
        User user5 = createUser("Anežka", "Liptáková", "xliptak", false, "passHash3");
        User user6 = createUser("Bohumil", "Neřád", "xnerad", false, "passHash4");
        User user7 = createUser("Kazimír", "Florence", "xflorence", false, "passHash5");
        User user8 = createUser("Fridrich", "Hoffman", "xhoffman", false, "passHash6");
        User user9 = createUser("Radoslav", "Tričkar", "xtrickar", false, "passHash7");
        User user10 = createUser("Tomáš", "Sekanina", "xsekanina", false, "passHash8");
        User user11 = createUser("Alan", "Miazga", "xmiazga", false, "passHash9");
        User user12 = createUser("Františka", "Bodnárová", "xbodnar", false, "passHash10");
        User user13 = createUser("Bruno", "Natyin", "xnatyin", true, "passHash11");
        User user14 = createUser("Angela", "Macron", "xmacron", true, "passHash12");
        User user15 = createUser("Gregor", "Jaslo", "xjaslo", true, "passHash13");

        // Study groups
        StudyGroup group1 = createGroup("englishStudyGroup");
        StudyGroup group2 = createGroup("thailandStudyGroup");
        StudyGroup group3 = createGroup("spanishStudyGroup");
        StudyGroup group4 = createGroup("frenchStudyGroup");

        // adding teachers
        addUserToStudyGroup(user1,group1);
        addUserToStudyGroup(user13, group2);
        addUserToStudyGroup(user14, group3);
        addUserToStudyGroup(user15, group4);

        //adding students
        addUserToStudyGroup(user2, group1);
        addUserToStudyGroup(user3, group2);
        addUserToStudyGroup(user4, group3);
        addUserToStudyGroup(user5, group4);
        addUserToStudyGroup(user6, group2);
        addUserToStudyGroup(user7, group4);
        addUserToStudyGroup(user8, group1);
        addUserToStudyGroup(user9, group2);
        addUserToStudyGroup(user10, group3);
        addUserToStudyGroup(user11, group4);
        addUserToStudyGroup(user12, group4);

        // Timelines
        HistoricalTimeline timeline1 = createTimeline("Ancient Rome", group1);
        HistoricalTimeline timeline2 = createTimeline("Ancient Greece", group2);
        HistoricalTimeline timeline3 = createTimeline("WW1", group1);
        HistoricalTimeline timeline4 = createTimeline("WW2", group2);
        HistoricalTimeline timeline5 = createTimeline("Evolution of European Union", group3);
        HistoricalTimeline timeline6 = createTimeline("British Empire", group4);

        // Comments
        TimelineComment comment1 = createComment("Interesting", user2, timeline1);
        TimelineComment comment2 = createComment("Where is Sparta?", user3, timeline2);
        TimelineComment comment3 = createComment("I don't like it", user5, timeline3);
        TimelineComment comment4 = createComment("Learn", user1, timeline4);
        TimelineComment comment5 = createComment("Strated by Schumman", user7, timeline5);
        TimelineComment comment6 = createComment("Currenly lead by queen", user8, timeline6);
        TimelineComment comment7 = createComment("Who won?", user6, timeline3);
        TimelineComment comment8 = createComment("Not Germany", user13, timeline3);
        TimelineComment comment9 = createComment("Shortcut is EU", user14, timeline5);

        // TODO Events
        HistoricalEvent event1 = createEvent("Funeral of Pope", LocalDate.of(2005,4,8), "Rome", "Peter", timeline1);
        HistoricalEvent event2 = createEvent("Birth of Jesus", LocalDate.of(0,12,24), "Betlehem", "Son of Maria", timeline1);
        HistoricalEvent event3 = createEvent("Battle of Thermophyls", LocalDate.of(75,8,19), "Thermopyles", "Bravery of Sparta", timeline2);
        HistoricalEvent event4 = createEvent("First task of Herculen", LocalDate.of(33, 2,15), "Nemea", "Nemean lion", timeline2);
        HistoricalEvent event5 = createEvent("Twelth task of Hercules", LocalDate.of(34,3,16), "Underworld", "Cerberus", timeline2);
        HistoricalEvent event6 = createEvent("Assasination of Franz Joseph", LocalDate.of(1916,6,18), "Sarajevo", "Beginning of war", timeline3);
        HistoricalEvent event7 = createEvent("Capitulation of Germany", LocalDate.of(1918,11,11), "Train Campaigne", "End of fighting", timeline3);
        HistoricalEvent event8 = createEvent("Start of WW2", LocalDate.of(1939,9,1), "Poland", "Attack performed by Germany / CCCP", timeline4);
        HistoricalEvent event9 = createEvent("End of WW2", LocalDate.of(1945,5,8), "Berlin", "Germany's capitulation", timeline4);
        HistoricalEvent event10 = createEvent("Foundation of ECC", LocalDate.of(1957,2,15), "Rome", "European Economic Comunity by The six", timeline5);
        HistoricalEvent event11 = createEvent("Nobel price", LocalDate.of(2012,3,3), "Oslo", "Nobel price for the peace", timeline5);
        HistoricalEvent event12 = createEvent("Establishment", LocalDate.of(1600,03,01), "London", "By Queen Elisabeth", timeline6);
    }

    private User createUser(String firstName, String lastName, String username, boolean isTeacher, String password) {
        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setIsTeacher(isTeacher);
        userService.registerUser(user, password);
        return user;
    }

    private StudyGroup createGroup(String name){
        StudyGroup group = new StudyGroup();
        group.setName(name);
        groupService.createStudyGroup(group);
        return group;
    }

    private void addUserToStudyGroup(User user, StudyGroup group){
        group.addUser(user);
        groupService.updateStudyGroup(group);
    }

    private HistoricalTimeline createTimeline(String name, StudyGroup group){
        HistoricalTimeline timeline = new HistoricalTimeline();
        timeline.setName(name);
        timelineService.createTimelineInStudyGroup(timeline,group);
        return timeline;
    }

    private TimelineComment createComment(String text, User user, HistoricalTimeline timeline){
        TimelineComment comment = new TimelineComment();
        comment.setText(text);
        comment.setUser(user);
        commentService.createTimelineCommentInTimeline(comment, timeline);
        return comment;
    }

    private HistoricalEvent createEvent(String name, LocalDate date, String location, String description, HistoricalTimeline timeline){
        HistoricalEvent event = new HistoricalEvent();
        event.setName(name);
        event.setDate(date);
        event.setLocation(location);
        event.setDescription(description);
        eventService.createEventInTimeline(event, timeline);
        return event;
    }


}
