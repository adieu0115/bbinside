package doubledrg.bbinside.domain.users;

import lombok.Data;

@Data
public class User
{
    private Long id;
    private String username;
    private String password;

    public User()
    {
    }
}
