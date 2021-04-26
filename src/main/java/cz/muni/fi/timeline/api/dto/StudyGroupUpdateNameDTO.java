package cz.muni.fi.timeline.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * DTO used for renaming StudyGroup.
 *
 * @author Tri Le Mau
 */
@Getter
@Setter
public class StudyGroupUpdateNameDTO {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 2, max = 500)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudyGroupUpdateNameDTO)) return false;
        StudyGroupUpdateNameDTO that = (StudyGroupUpdateNameDTO) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
