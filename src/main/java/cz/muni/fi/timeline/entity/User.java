package cz.muni.fi.timeline.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * This entity class represents a User
 * Contains study groups where User is enrolled in
 *
 * @author Tri Le Mau
 *
 * TODO change javadoc
 */
@Table(name = "UserTable")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
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
    private String hashedPassword;

    // TODO
    private boolean isTeacher;

    // TODO
    @ManyToMany(mappedBy = "users")
    private Set<StudyGroup> studyGroups;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User that = (User) o;
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
