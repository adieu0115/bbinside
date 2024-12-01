package doubledrg.bbinside.domain.users.dto;

import lombok.Data;

@Data
public class UserLoginDto
{
    private String username;
    private String password;

    public UserLoginDto(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}
