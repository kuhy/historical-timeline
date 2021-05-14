package cz.muni.fi.timeline.data;

import cz.muni.fi.timeline.entity.User;
import cz.muni.fi.timeline.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;


/**
 * Implementation of TimelineDataLoader.
 *
 * @author Ondřej Kuhejda
 */
@Component
@Transactional
public class TimelineDataLoaderImpl implements TimelineDataLoader {

    private final UserService userService;

    @Inject
    public TimelineDataLoaderImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void loadData() {
        // Users
        createUser("kuhy", "Ondřej", "Kuhejda", false, "hovnokleslo");
        createUser("amos", "Jan", "Komenský", true, "skakalzoknanazensky");

        // TODO Study groups

        // TODO Timelines

        // TODO Comments

        // TODO Events

    }

    private void createUser(String username, String firstName, String lastName, boolean isTeacher, String password) {
        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setIsTeacher(isTeacher);
        userService.registerUser(user, password);
    }
}
