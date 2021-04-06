package cz.muni.fi.timeline.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
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
    private Set<Student> students;

    /**
     * Returns all students of study group.
     *
     * @return all students of study group
     */
    public Set<Student> getStudents() {
        return Collections.unmodifiableSet(students);
    }

    /**
     * Adds student to study group.
     *
     * @param student student that is added to study group.
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * Removes student from study group
     *
     * @param student student that is removed from study group
     */
    public void removeStudent(Student student) {
        students.remove(student);
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
