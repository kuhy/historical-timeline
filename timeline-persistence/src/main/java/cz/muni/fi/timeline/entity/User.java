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
@Getter
@Setter
@Entity
@Table(name = "user_entity")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable=false, name = "first_name")
    private String firstName;

    @Column(nullable=false, name = "last_name")
    private String lastName;

    @Column(nullable=false)
    private String username;

    @Column(nullable=false, name = "hashed_password")
    private String hashedPassword;

    @Column(nullable=false, name = "is_teacher")
    private Boolean isTeacher;

    @ManyToMany(mappedBy = "users")
    private Set<StudyGroup> studyGroups = new HashSet<>();

    /**
     * Adds study group to user.
     *
     * @param studyGroup study group that is added to user.
     */
    public void addStudyGroup(StudyGroup studyGroup) {
        studyGroup.addUser(this);
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

    @Override
    public String toString() {
        return "User{" +
            "firstName='" + getFirstName() + '\'' +
            ", lastName='" + getLastName() + '\'' +
            ", username='" + getUsername() + '\'' +
            ", hashedPassword='" + getHashedPassword() + '\'' +
            ", isTeacher=" + getIsTeacher() +
            '}';
    }
}
