package cz.muni.fi.timeline.api.dto;

import cz.muni.fi.timeline.entity.StudyGroup;
import lombok.Data;
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
@Data
public class StudyGroupCreateDTO {

    @NotNull
    @Size(min = 2, max = 500)
    private String name;
}
