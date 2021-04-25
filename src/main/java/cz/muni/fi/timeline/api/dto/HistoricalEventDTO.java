package cz.muni.fi.timeline.api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class HistoricalEventDTO {

    @Getter
    @Setter
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 2, max = 500)
    private String name;

    @NotNull
    @Size(min = 2, max = 500)
    private String description;

    @NotNull
    private LocalDate date;

    @NotNull
    @Size(min = 2, max = 500)
    private String location;

    @Lob
    @NotNull
    private byte[] image;
}
