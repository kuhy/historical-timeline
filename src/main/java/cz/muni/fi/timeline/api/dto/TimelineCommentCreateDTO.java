package cz.muni.fi.timeline.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TimelineCommentCreateDTO {

    @Getter
    @Setter
    @NotNull
    @Size(min = 2, max = 500)
    private String text;
}
