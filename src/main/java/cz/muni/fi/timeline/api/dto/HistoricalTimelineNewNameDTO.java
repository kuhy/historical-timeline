package cz.muni.fi.timeline.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class HistoricalTimelineNewNameDTO {

    @Getter
    @Setter
    @NotNull
    private Long id;

    @NotNull
    @Getter
    @Setter
    @Size(min = 2, max = 500)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoricalTimelineNewNameDTO)) return false;
        HistoricalTimelineNewNameDTO that = (HistoricalTimelineNewNameDTO) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
