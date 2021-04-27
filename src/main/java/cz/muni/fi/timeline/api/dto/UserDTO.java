package cz.muni.fi.timeline.api.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * DTO for user.
 *
 * @author Karolína Veselá
 */
@Getter
@Setter
public class UserDTO {
    @NotNull
    private long id;
    @NotNull
    @Size(min = 2, max = 500)
    private String firstName;
    @NotNull
    @Size(min = 2, max = 500)
    private String lastName;
    @NotNull
    @Size(min = 2, max = 500)
    private String username;
    @NotNull
    private String hashedPassword;

    private Boolean isTeacher;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO that = (UserDTO) o;
        return Objects.equals(getFirstName(), that.getFirstName())
                && Objects.equals(getLastName(), that.getLastName())
                && Objects.equals(getUsername(), that.getUsername())
                && Objects.equals(getHashedPassword(), that.getHashedPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getUsername(), getHashedPassword());
    }
}
