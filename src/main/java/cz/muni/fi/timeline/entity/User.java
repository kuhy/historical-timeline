package cz.muni.fi.timeline.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This entity class represents a User
 * Contains study groups where User is enrolled in
 *
 * @author Tri Le Mau
 */
@Entity
@Table(name = "user_entity")
public class User implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Setter
    @Column(nullable=false)
    private String firstName;

    @Getter
    @Setter
    @Column(nullable=false)
    private String lastName;

    @Getter
    @Setter
    @Column(nullable=false)
    private String username;

    @Getter
    @Setter
    @Column(nullable=false)
    private String hashedPassword;

    @Getter
    @Setter
    @Column(nullable=false)
    private Boolean isTeacher;

    @ManyToMany(mappedBy = "users")
    private Set<StudyGroup> studyGroups = new HashSet<>();

    /**
     * Returns all study groups of user.
     *
     * @return all study groups of user
     */
    public Set<StudyGroup> getStudyGroups() {
        return Collections.unmodifiableSet(studyGroups);
    }

    /**
     * Adds study group to user.
     *
     * @param studyGroup study group that is added to user.
     */
    public void addStudyGroup(StudyGroup studyGroup) {
        studyGroups.add(studyGroup);
    }

    /**
     * Removes study group from user
     *
     * @param studyGroup study group that is removed from user
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
