package online.market.client.controller;


import lombok.RequiredArgsConstructor;
import online.market.model.entity.Customer;
import online.market.service.common.OrderService;
import online.market.service.entity.CustomerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final CustomerService customerService;
    private final OrderService orderService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @RequestMapping("/my-account")
    public String myAccount(Model model)
    {
        model.addAttribute("classActiveMyAccount", "home active");
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String currentUserName=authentication.getName();
//      Get logged in customer
        Customer customer=customerService.findByUsername(currentUserName);
        model.addAttribute("customer",customer);
        return "/userAccount/UserAccount";
    }
}
