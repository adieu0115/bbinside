package doubledrg.bbinside.domain.users;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Users
{
    private Long id;
    private String username;
    private String password;

    public Users(Long id, String username, String password)
    {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
