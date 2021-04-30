package cz.muni.fi.timeline.api.dto;

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
@Getter
@Setter
public class StudyGroupDTO {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 2, max = 500)
    private String name;

    @NotNull
    private Set<UserDTO> users = new HashSet<>();

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
