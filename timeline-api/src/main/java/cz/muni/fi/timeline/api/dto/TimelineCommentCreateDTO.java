package cz.muni.fi.timeline.api.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for creating timeline comment.
 *
 * @author Matej Macák
 */
@Data
public class TimelineCommentCreateDTO {

    @NotNull
    @Size(min = 2, max = 500)
    private String text;

}
