package cz.muni.fi.timeline.api.dto;

import cz.muni.fi.timeline.entity.StudyGroup;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO used for Study Group.
 *
 * @author Tri Le Mau
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudyGroupDTO)) return false;
        StudyGroupDTO that = (StudyGroupDTO) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
