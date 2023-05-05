package online.market.service.common;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Customer;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final Environment environment;

    public boolean testEmail() {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(environment.getProperty("to.email"));
            simpleMailMessage.setSubject("Testing email");
            simpleMailMessage.setText("Dear folk, is this testing email sent by Ecommerce application !");
            simpleMailMessage.setFrom(environment.getProperty("from.email"));

            javaMailSender.send(simpleMailMessage);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean registration(String appUrl, Customer customer) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(customer.getUsername());//should be email address
            simpleMailMessage.setSubject("Kitoblarining elektron savdosini oshiruvchi tizim ");

            String loginUrl = appUrl + "/login";

            simpleMailMessage.setText(
                    String.format("Assalom alaykum, %s, Bizning online kutubxona tizimimizga a'zo bolganingiz uchun rahmat!." +
                                    "\n iltimos ro'yhatdan o'ting %s"
                            , customer.getFullName(), loginUrl));
            simpleMailMessage.setFrom(environment.getProperty("from.email"));

            javaMailSender.send(simpleMailMessage);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean orderCreation() {
        return true;
    }
}
