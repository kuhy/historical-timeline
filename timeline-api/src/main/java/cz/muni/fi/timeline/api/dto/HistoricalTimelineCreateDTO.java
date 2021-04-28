package cz.muni.fi.timeline.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * DTO for creating historical timeline.
 *
 * @author Matej Macák
 */
@Data
public class HistoricalTimelineCreateDTO {

    @NotNull
    @Size(min = 2, max = 500)
    private String name;

}
