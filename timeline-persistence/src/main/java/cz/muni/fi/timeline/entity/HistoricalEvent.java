package cz.muni.fi.timeline.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Entity class representing concrete historic event
 * Contains name, brief description, data location and can contain image or placeholder
 * @author Matej Macák
 */
@Getter
@Setter
@Entity
@Table(name = "event_entity")
public class HistoricalEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable=false)
    private String name;

    private String description;

    private LocalDate date;

    private String location;

    private String image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoricalEvent)) return false;
        HistoricalEvent that = (HistoricalEvent) o;
        return Objects.equals(getName(), that.getName())
                && Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(getDate(), that.getDate())
                && Objects.equals(getLocation(), that.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getDate(), getLocation());
    }

    @Override
    public String toString() {
        return "HistoricalEvent{" +
            "name='" + getName() + '\'' +
            ", description='" + getDescription() + '\'' +
            ", date=" + getDate() +
            ", location='" + getLocation() + '\'' +
            '}';
    }
}
