package cz.muni.fi.timeline.api.dto;

import cz.muni.fi.timeline.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
public class UserDTO {
    @NotNull
    private long ID;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String username;
    @NotNull
    private String hashedPassword;

    private Boolean isTeacher;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
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
