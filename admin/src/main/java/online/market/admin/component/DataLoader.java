package online.market.admin.component;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Role;
import online.market.model.entity.Users;
import online.market.model.repository.RoleRepository;
import online.market.model.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup)
            return;
        //Setup roles
        createRoleIfNotFound("ADMIN");
        createRoleIfNotFound("CUSTOMER");
        createUserIfNotFound("temur@mail.ru");
    }

    @Transactional
    Role createRoleIfNotFound(String name) {
        Role role = roleRepository.findByRoleName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
            role = roleRepository.findByRoleName(name);
        }
        return role;
    }

    @Transactional
    Users createUserIfNotFound(String username) {
        Users users = userRepository.findByUsername(username);
        if (users == null) {
            users = new Users();
            users.setIsActive(true);
            users.setFirstName("Temurbek");
            users.setLastName("Zarifov");
            users.setPassword(bCryptPasswordEncoder.encode("123456"));
            users.setUsername(username);
            Role roleForAdmin = roleRepository.findByRoleName("ADMIN");
            List<Role> roles = new ArrayList<>();
            roles.add(roleForAdmin);
            users.setRoles(roles);
            userRepository.save(users);
        }
        return users;
    }
}
