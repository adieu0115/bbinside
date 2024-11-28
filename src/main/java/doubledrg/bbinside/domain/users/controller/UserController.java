package doubledrg.bbinside.domain.users.controller;

import doubledrg.bbinside.domain.users.Users;
import doubledrg.bbinside.domain.users.dto.UserSaveDto;
import doubledrg.bbinside.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController
{
    private final UserService userService;

    @PostMapping()
    public Users save(@RequestBody UserSaveDto users)
    {
        return userService.save(users);
    }
}
