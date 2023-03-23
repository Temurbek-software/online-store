package online.market.service.entity.impl;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Customer;
import online.market.model.entity.Role;
import online.market.model.entity.Users;
import online.market.model.payload.CustomerDto;
import online.market.model.repository.CustomerRepository;
import online.market.model.repository.RoleRepository;
import online.market.model.repository.UserRepository;
import online.market.service.entity.CustomerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword()
                , mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }

    @Override
    public Customer save(CustomerDto registrationDto) {
        Customer customer = new Customer();
        customer.setFirstName(registrationDto.getFirstName());
        customer.setPostalCode(registrationDto.getPostalCode());
        customer.setAddress(registrationDto.getAddress());
        customer.setUsername(registrationDto.getUsername());
        customer.setLastName(registrationDto.getLastName());
        customer.setPhoneNumber(registrationDto.getPhoneNumber());
        customer.setCountryName(registrationDto.getCountryName());
        customer.setCityName(registrationDto.getCityName());
        customer.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        customer.setRoles(Arrays.asList(roleRepository.findByRoleName("CUSTOMER")));
        return customerRepository.save(customer);
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }
}
