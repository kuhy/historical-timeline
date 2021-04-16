package cz.muni.fi.timeline.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This entity class represents a study group
 * Contains students of the study group
 *
 * @author Tri Le Mau
 */

@Entity
public class StudyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @NotNull
    @Getter
    @Setter
    private String name;

    @ManyToMany
    private Set<User> users = new HashSet<>();

    @OneToMany
    @JoinColumn
    private Set<HistoricalTimeline> historicalTimelines = new HashSet<>();

    /**
     * Returns all users of study group.
     *
     * @return all users of study group
     */
    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    /**
     * Adds user to study group.
     *
     * @param user user that is added to study group.
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Removes user from study group
     *
     * @param user user that is removed from study group
     */
    public void removeUser(User user) {
        users.remove(user);
    }

    /**
     * Adds timeline to study group.
     *
     * @param timeline timeline that is added to study group.
     */
    public void addHistoricalTimeline(HistoricalTimeline timeline) {
        historicalTimelines.add(timeline);
    }

    /**
     * Removes timeline from study group
     *
     * @param timeline timeline that is removed from study group
     */
    public void removeHistoricalTimeline(HistoricalTimeline timeline) {
        historicalTimelines.remove(timeline);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudyGroup)) return false;
        StudyGroup that = (StudyGroup) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
