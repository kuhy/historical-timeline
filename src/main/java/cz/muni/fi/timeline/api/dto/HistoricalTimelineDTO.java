package cz.muni.fi.timeline.api.dto;

import cz.muni.fi.timeline.entity.HistoricalTimeline;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class HistoricalTimelineDTO {

    @Getter
    @Setter
    @NotNull
    private Long id;

    @Getter
    @Setter
    @NotNull
    @Size(min = 2, max = 500)
    private String name;

    @Getter
    @Setter
    @NotNull
    private Set<HistoricalEventDTO> historicalEvents = new HashSet<>();

    @Getter
    @Setter
    @NotNull
    private Set<TimelineCommentDTO> timelineComments = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoricalTimelineDTO)) return false;
        HistoricalTimelineDTO that = (HistoricalTimelineDTO) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
