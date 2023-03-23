package online.market.service.entity.impl;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Role;
import online.market.model.entity.Users;
import online.market.model.payload.UserDto;
import online.market.model.repository.RoleRepository;
import online.market.model.repository.UserRepository;
import online.market.service.entity.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Users save(UserDto registrationDto) {
        Users users = new Users();
        users.setUsername(registrationDto.getUsername());
        users.setFirstName(registrationDto.getFirstName());
        users.setPassword(registrationDto.getPassword());
        users.setRoles(Arrays.asList(roleRepository.findByRoleName("ADMIN")));
        users.setIsActive(true);
        return userRepository.save(users);
    }

    @Override
    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(s);
        if (users == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new User(users.getUsername(), users.getPassword(), mapRolesToAuthorities(users.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }

}
