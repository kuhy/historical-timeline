package cz.muni.fi.timeline.api.dto;

import cz.muni.fi.timeline.entity.StudyGroup;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class StudyGroupNewNameDTO {
    @Getter
    @Setter
    @NotNull
    private Long id;

    @Getter
    @Setter
    @NotNull
    @Size(min = 2, max = 500)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudyGroupNewNameDTO)) return false;
        StudyGroupNewNameDTO that = (StudyGroupNewNameDTO) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
