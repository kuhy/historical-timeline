package cz.muni.fi.timeline.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class StudyGroupDTO {

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
    private Set<UserDTO> users = new HashSet<>();

    @Getter
    @Setter
    @NotNull
    private Set<HistoricalTimelineDTO> historicalTimelines = new HashSet<>();

    // TODO: equals and hash
}
