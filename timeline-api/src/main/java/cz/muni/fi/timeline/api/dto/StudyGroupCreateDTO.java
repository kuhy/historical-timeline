package cz.muni.fi.timeline.api.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
