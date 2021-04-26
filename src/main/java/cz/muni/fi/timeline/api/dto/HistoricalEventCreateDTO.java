package cz.muni.fi.timeline.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

public class HistoricalEventCreateDTO {
    @Getter
    @Setter
    @NotNull
    @Size(min = 2, max = 500)
    private String name;

    @Getter
    @Setter
    @NotNull
    @Size(min = 2, max = 500)
    private String description;

    @Getter
    @Setter
    @NotNull
    @Size(min = 2, max = 500)
    private String location;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoricalEventCreateDTO)) return false;
        HistoricalEventCreateDTO that = (HistoricalEventCreateDTO) o;
        return Objects.equals(getName(), that.getName())
                && Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(getLocation(), that.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getLocation());    }
}
