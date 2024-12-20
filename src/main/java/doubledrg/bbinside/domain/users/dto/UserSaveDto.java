package doubledrg.bbinside.domain.users.dto;

import lombok.Data;
import lombok.Setter;

@Data
public class UserSaveDto
{
    private String username;
    private String password;

    public UserSaveDto(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}
