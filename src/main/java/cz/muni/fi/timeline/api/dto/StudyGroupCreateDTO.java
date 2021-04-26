package cz.muni.fi.timeline.api.dto;

import cz.muni.fi.timeline.entity.StudyGroup;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * DTO used for creating StudyGroup.
 *
 * @author Tri Le Mau
 */
public class StudyGroupCreateDTO {

    @Getter
    @Setter
    @NotNull
    @Size(min = 2, max = 500)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudyGroupCreateDTO)) return false;
        StudyGroupCreateDTO that = (StudyGroupCreateDTO) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}