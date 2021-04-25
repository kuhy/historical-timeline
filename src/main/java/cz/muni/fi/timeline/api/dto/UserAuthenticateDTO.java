package cz.muni.fi.timeline.api.dto;

import cz.muni.fi.timeline.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
public class UserAuthenticateDTO {
    @NotNull
    private long ID;
    @NotNull
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        UserAuthenticateDTO that = (UserAuthenticateDTO) o;
        return Objects.equals(getID(), that.getID())
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }
}
