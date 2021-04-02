package cz.muni.fi.timeline.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entity class that represents teacher.
 * Teacher can own multiple study groups.
 *
 * @author Karolína Veselá
 */

@Getter
@Setter
@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String hashedPassword;


    @OneToMany
    @JoinColumn
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    public Set<StudyGroup> studyGroups = new HashSet<>();

    public void addStudyGroup(StudyGroup studyGroup) {
        this.studyGroups.add(studyGroup);
    }

    public Set<StudyGroup> getStudyGroups() {
        return Collections.unmodifiableSet(studyGroups);
    }

    public void removeStudyGroup(StudyGroup studyGroup) {
        this.studyGroups.remove(studyGroup);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return getFirstName().equals(teacher.getFirstName()) && getLastName().equals(teacher.getLastName()) && getUsername().equals(teacher.getUsername()) && getHashedPassword().equals(teacher.getHashedPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getUsername(), getHashedPassword());
    }
}
