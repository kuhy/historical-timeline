package cz.muni.fi.timeline.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StudyGroupCreateDTO {

    @Getter
    @Setter
    @NotNull
    @Size(min = 2, max = 500)
    private String name;

    // TODO: equals and hash
}
