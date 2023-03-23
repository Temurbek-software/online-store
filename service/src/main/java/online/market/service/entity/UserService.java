package online.market.service.entity;

import online.market.model.entity.Users;
import online.market.model.payload.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService
{
    Users save(UserDto registrationDto);
    Users findByUsername(String username);
}
