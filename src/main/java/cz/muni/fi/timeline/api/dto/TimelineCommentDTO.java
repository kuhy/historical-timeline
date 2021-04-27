package cz.muni.fi.timeline.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * DTO for timeline comment.
 *
 * @author Matej Macák
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimelineCommentDTO)) return false;
        TimelineCommentDTO that = (TimelineCommentDTO) o;
        return Objects.equals(getText(), that.getText()) && Objects.equals(getUser(), that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), getUser());
    }
}
