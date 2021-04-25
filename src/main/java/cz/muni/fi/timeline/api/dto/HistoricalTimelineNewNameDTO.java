package cz.muni.fi.timeline.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
}
