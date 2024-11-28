package doubledrg.bbinside.domain.users.service;

import doubledrg.bbinside.domain.users.Users;
import doubledrg.bbinside.domain.users.dao.UserDao;
import doubledrg.bbinside.domain.users.dto.UserSaveDto;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService
{
    private final UserDao userDao;

    public Users save(UserSaveDto users)
    {
        return userDao.save(users);
    }
}
