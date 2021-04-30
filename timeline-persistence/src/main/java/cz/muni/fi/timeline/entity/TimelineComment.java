package cz.muni.fi.timeline.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * Entity class that represents comment inside timeline.
 * Holds text and references student that created this comment.
 *
 * @author Ondřej Kuhejda
 */
@Getter
@Setter
@Entity
@Table(name = "comment_entity")
public class TimelineComment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable=false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimelineComment)) return false;
        TimelineComment that = (TimelineComment) o;
        return Objects.equals(getText(), that.getText()) && Objects.equals(getUser(), that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), getUser());
    }

    @Override
    public String toString() {
        return "TimelineComment{" +
            "text='" + getText() + '\'' +
            ", user=" + getUser() +
            '}';
    }
}
