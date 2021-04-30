package cz.muni.fi.timeline.api.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;


/**
 * DTO for creating historical event.
 *
 * @author Matej Macák
 */
@Data
public class HistoricalEventCreateDTO {
    @NotNull
    @Size(min = 2, max = 500)
    private String name;

    @NotNull
    @Size(min = 2, max = 500)
    private String description;

    @NotNull
    @Size(min = 2, max = 500)
    private String location;

    @NotNull
    private LocalDate date;


}
