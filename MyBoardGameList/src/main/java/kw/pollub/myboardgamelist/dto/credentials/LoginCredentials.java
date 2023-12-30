package kw.pollub.myboardgamelist.dto.credentials;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginCredentials {
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;
}
