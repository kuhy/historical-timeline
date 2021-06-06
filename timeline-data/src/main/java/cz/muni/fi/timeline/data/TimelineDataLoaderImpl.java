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

        // Events
        HistoricalEvent event1 = createEvent("Funeral of Pope", LocalDate.of(2005,4,8), "Rome", "Peter", timeline1, "https://imageshistorical.imgix.net/https%3A%2F%2Fwww.orderofmalta.int%2Fwp-content%2Fuploads%2F2005%2F04%2F11_04_2005_01.jpg?s=d5243f4b9b6f7274804d032dcb0dab41");
        HistoricalEvent event2 = createEvent("Birth of Jesus", LocalDate.of(0,12,24), "Betlehem", "Son of Maria", timeline1, "https://imageshistorical.imgix.net/https%3A%2F%2Freligionworld.s3.amazonaws.com%2Fuploads%2F2018%2F12%2Fjesus.jpg?s=40926420e67bd880828a2b67ed4b0fa0");
        HistoricalEvent event3 = createEvent("Battle of Thermophyls", LocalDate.of(75,8,19), "Thermopyles", "Bravery of Sparta", timeline2, "https://imageshistorical.imgix.net/https%3A%2F%2Fstmuhistorymedia.org%2Fwp-content%2Fuploads%2Felementor%2Fthumbs%2F11ca891f186837215470c142fc7001e8-osa636qtfl4026ghx5aj2db7lr3czue5ngzp6bpcuw.jpg?s=50d22674bf39816e88017d33c4703325\n");
        HistoricalEvent event4 = createEvent("First task of Herculen", LocalDate.of(33, 2,15), "Nemea", "Nemean lion", timeline2, "https://imageshistorical.imgix.net/https%3A%2F%2Fi.pinimg.com%2F736x%2F66%2F1e%2F63%2F661e63cc33f680d1961174dca7789cdf.jpg?s=72ed2e891d123fce22c7c24bfefd3d7c");
        HistoricalEvent event5 = createEvent("Twelth task of Hercules", LocalDate.of(34,3,16), "Underworld", "Cerberus", timeline2, "https://imageshistorical.imgix.net/http%3A%2F%2Fwww.perseus.tufts.edu%2FHerakles%2FHpix%2F1992.06.0470.jpeg?s=4e08b2d8751de6a057403ab2f79f647b");
        HistoricalEvent event6 = createEvent("Assasination of Franz Joseph", LocalDate.of(1916,6,18), "Sarajevo", "Beginning of war", timeline3, "https://imageshistorical.imgix.net/https%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2Fe%2Fee%2FEmperor_Francis_Joseph.jpg?s=35add243805804c07aa390538f97f448");
        HistoricalEvent event7 = createEvent("Capitulation of Germany", LocalDate.of(1918,11,11), "Train Campaigne", "End of fighting", timeline3, "https://imageshistorical.imgix.net/https%3A%2F%2Fstatic.politico.com%2Fac%2F3e%2F1de3799d4f45b5bd23204f23b096%2Fwebp.net-resizeimage%2520(7).jpg?s=3aacec56cbcbeb85cd0e405d1ef7e11e");
        HistoricalEvent event8 = createEvent("Start of WW2", LocalDate.of(1939,9,1), "Poland", "Attack performed by Germany / CCCP", timeline4, "https://imageshistorical.imgix.net/https%3A%2F%2Fs3.amazonaws.com%2Fs3.timetoast.com%2Fpublic%2Fuploads%2Fphoto%2F6616254%2Fimage%2F95fded5c11f130d24d46745a96209d09?s=8765d21fe16d064464134ca51514dcc7");
        HistoricalEvent event9 = createEvent("End of WW2", LocalDate.of(1945,5,8), "Berlin", "Germany's capitulation", timeline4, "https://imageshistorical.imgix.net/https%3A%2F%2Fmediacloud.theweek.co.uk%2Fimage%2Fprivate%2Fs--lmkiDPuB--%2Fv1603038728%2Ftheweek%2F2015%2F05%2F150505-vedaymain.jpg?s=65e264e2b9c605f7ed7a7096c50a3b73");
        HistoricalEvent event10 = createEvent("Foundation of ECC", LocalDate.of(1957,2,15), "Rome", "European Economic Comunity by The six", timeline5, "https://imageshistorical.imgix.net/https%3A%2F%2Fcdn.britannica.com%2F33%2F163833-050-5EF4B043%2FSigning-Treaty-of-Rome-March-25-1957.jpg?s=df1138bf71dfea9eb040a68c7c057e2c");
        HistoricalEvent event11 = createEvent("Nobel price", LocalDate.of(2012,3,3), "Oslo", "Nobel price for the peace", timeline5, "https://imageshistorical.imgix.net/https%3A%2F%2Fi1.sndcdn.com%2Favatars-000106982112-rq9ru4-t240x240.jpg?s=85dd7ddc15218d91e18577fff8d9afa2");
        HistoricalEvent event12 = createEvent("Establishment", LocalDate.of(1600,03,01), "London", "By Queen Elisabeth", timeline6, "https://imageshistorical.imgix.net/https%3A%2F%2Fcdn.britannica.com%2F85%2F285-050-EB9954ED%2FElizabeth-I-oil-panel-George-Gower-1588.jpg?s=4680ddb9eec7c0a1aa68954514a9a2c4");
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

    private HistoricalEvent createEvent(String name, LocalDate date, String location, String description, HistoricalTimeline timeline, String image){
        HistoricalEvent event = new HistoricalEvent();
        event.setName(name);
        event.setDate(date);
        event.setLocation(location);
        event.setDescription(description);
        event.setImage(image);
        eventService.createEventInTimeline(event, timeline);
        return event;
    }


}
