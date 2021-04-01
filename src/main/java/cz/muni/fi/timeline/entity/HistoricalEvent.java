package cz.muni.fi.timeline.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

/**
 * Entity class representing concrete historic event
 * Contains name, brief description, data location and can contain image or placeholder
 * @author Matej Macák
 */
@Entity
public class HistoricalEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private LocalDate date;

    @Getter
    @Setter
    private String location;

    @Lob
    @Getter
    @Setter
    private byte[] image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoricalEvent)) return false;
        HistoricalEvent that = (HistoricalEvent) o;
        return Objects.equals(getName(), that.getName())
                && Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(getDate(), that.getDate())
                && Objects.equals(getLocation(), that.getLocation())
                && Arrays.equals(getImage(), that.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getDate(), getLocation(), getImage());
    }
}
