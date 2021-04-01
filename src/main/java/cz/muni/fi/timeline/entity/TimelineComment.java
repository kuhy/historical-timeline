package cz.muni.fi.timeline.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;


/**
 * Entity class that represents comment inside timeline.
 * Holds text and references student that created this comment.
 *
 * @author Ondřej Kuhejda
 */
@Entity
@Getter
@Setter
public class TimelineComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimelineComment)) return false;
        TimelineComment that = (TimelineComment) o;
        return Objects.equals(getText(), that.getText()) && Objects.equals(getStudent(), that.getStudent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), getStudent());
    }
}
