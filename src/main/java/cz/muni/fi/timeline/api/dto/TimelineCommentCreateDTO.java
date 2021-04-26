package cz.muni.fi.timeline.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class TimelineCommentCreateDTO {

    @Getter
    @Setter
    @NotNull
    @Size(min = 2, max = 500)
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimelineCommentCreateDTO)) return false;
        TimelineCommentCreateDTO that = (TimelineCommentCreateDTO) o;
        return Objects.equals(getText(), that.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText());
    }
}
