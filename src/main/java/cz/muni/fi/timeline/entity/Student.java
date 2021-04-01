package cz.muni.fi.timeline.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * This entity class represents a student
 * Contains study groups where student is enrolled in
 *
 * @author Tri Le Mau
 */

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Getter
    @Setter
    private String firstName;

    @NotNull
    @Getter
    @Setter
    private String lastName;

    @NotNull
    @Getter
    @Setter
    private String username;

    @NotNull
    @Getter
    @Setter
    private int hashedPassword;

    @ManyToMany(mappedBy = "students")
    private Set<StudyGroup> studyGroups;

    @OneToMany
    private Set<TimelineComment> timelineComments;

    /**
     * Returns all study groups of student.
     *
     * @return all study groups of student
     */
    public Set<StudyGroup> getStudyGroups() {
        return Collections.unmodifiableSet(studyGroups);
    }

    /**
     * Adds study group to student.
     *
     * @param studyGroup student group that is added to student.
     */
    public void addStudyGroup(StudyGroup studyGroup) {
        studyGroups.add(studyGroup);
    }

    /**
     * Removes student group from student
     *
     * @param studyGroup student group that is removed from student
     */
    public void removeStudyGroup(StudyGroup studyGroup) {
        studyGroups.remove(studyGroup);
    }

    /**
     * Returns all timeline comments of student.
     *
     * @return all timeline comments of student
     */
    public Set<TimelineComment> getTimelineComments() {
        return Collections.unmodifiableSet(timelineComments);
    }

    /**
     * Adds timeline comment to student.
     *
     * @param timelineComment timeline comment that is added to student.
     */
    public void addTimelineComment(TimelineComment timelineComment) {
        timelineComments.add(timelineComment);
    }

    /**
     * Removes timeline comment from student
     *
     * @param timelineComment timeline comment that is removed from student
     */
    public void removeStudyGroup(TimelineComment timelineComment) {
        timelineComments.remove(timelineComment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student that = (Student) o;
        return Objects.equals(getFirstName(), that.getFirstName())
                && Objects.equals(getLastName(), that.getLastName())
                && Objects.equals(getUsername(), that.getUsername())
                && Objects.equals(getHashedPassword(), that.getHashedPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getUsername(), getHashedPassword());
    }
}