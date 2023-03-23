package online.market.service.common;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Customer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
//    private final JavaMailSender javaMailSender;
    private final Environment environment;

    public boolean testEmail() {
        return true;
    }

    public boolean registration(String urlName, Customer customer)
    {
        return true;
    }

    public boolean orderCreation() {
        return true;
    }
}
