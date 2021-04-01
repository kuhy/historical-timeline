package cz.muni.fi.timeline.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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