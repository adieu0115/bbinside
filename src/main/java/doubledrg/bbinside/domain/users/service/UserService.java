package doubledrg.bbinside.domain.users.service;

import doubledrg.bbinside.domain.users.User;
import doubledrg.bbinside.domain.users.dao.UserDao;
import doubledrg.bbinside.domain.users.dto.UserLoginDto;
import doubledrg.bbinside.domain.users.dto.UserLogoutDto;
import doubledrg.bbinside.domain.users.dto.UserSaveDto;

import java.sql.SQLException;

public class UserService
{
    private final UserDao userDao = new UserDao();

    public User signup(UserSaveDto dto) throws SQLException, ClassNotFoundException
    {
        User duplicateUser = userDao.findByName(dto.getUsername());
        User savedUser = null;
        if (duplicateUser == null)
        {
            Long id = userDao.save(dto);
            savedUser = userDao.findById(id);
        }
        else
        {
            throw new IllegalStateException("중복된 이름 예외!");
        }
        return savedUser;
    }

    public User login(UserLoginDto dto) throws SQLException, ClassNotFoundException
    {
        User findUser = userDao.findByNameAndPassword(dto);
        if (findUser == null)
        {
            throw new IllegalStateException("로그인 실패!");
        }
        else
        {
            return findUser;
        }
    }

    public void logOut(UserLogoutDto dto)
    {
        
    }
}
