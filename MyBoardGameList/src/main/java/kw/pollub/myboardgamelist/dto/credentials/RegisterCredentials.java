package kw.pollub.myboardgamelist.dto.credentials;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import kw.pollub.myboardgamelist.model.constraints.ValidPassword;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterCredentials {
    @NotEmpty
    private String name;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    @ValidPassword
    private String password;
}
