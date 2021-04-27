package cz.muni.fi.timeline.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

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
