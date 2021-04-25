package cz.muni.fi.timeline.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TimelineCommentDTO {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 2, max = 500)
    private String text;

    @NotNull
    private UserDTO user;
}
