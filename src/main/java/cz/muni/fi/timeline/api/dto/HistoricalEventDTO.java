package cz.muni.fi.timeline.api.dto;

import cz.muni.fi.timeline.entity.HistoricalEvent;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
public class HistoricalEventDTO {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 2, max = 500)
    private String name;

    @NotNull
    @Size(min = 2, max = 500)
    private String description;

    @NotNull
    private LocalDate date;

    @NotNull
    @Size(min = 2, max = 500)
    private String location;

    @Lob
    @NotNull
    private byte[] image;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoricalEventDTO)) return false;
        HistoricalEventDTO that = (HistoricalEventDTO) o;
        return Objects.equals(getName(), that.getName())
                && Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(getDate(), that.getDate())
                && Objects.equals(getLocation(), that.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getDate(), getLocation());
    }
}