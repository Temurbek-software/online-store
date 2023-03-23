package online.market.service.entity;

import online.market.model.entity.Customer;
import online.market.model.payload.CustomerDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomerService extends UserDetailsService {
    Customer save(CustomerDto registrationDto);//For insert
    Customer save(Customer customer);//For update
    Customer findByUsername(String username);
}
