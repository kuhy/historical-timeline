package cz.muni.fi.timeline.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entity class that represents teacher.
 * Teacher can own multiple study groups.
 *
 * @author Karolína Veselá
 */

@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
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


    @OneToMany
    @JoinColumn
    public Set<StudyGroup> studyGroups = new HashSet<>();

    public void setStudyGroups(StudyGroup studyGroup){
        this.studyGroups.add(studyGroup);
    }

    public Set<StudyGroup> getStudyGroups(){
        return this.studyGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(getId(), teacher.getId()) && getFirstName().equals(teacher.getFirstName()) && getLastName().equals(teacher.getLastName()) && getUsername().equals(teacher.getUsername()) && getHashedPassword().equals(teacher.getHashedPassword()) && Objects.equals(studyGroups, teacher.studyGroups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getUsername(), getHashedPassword());
    }
}
