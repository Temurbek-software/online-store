package online.market.client.controller;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Customer;
import online.market.model.payload.CustomerDto;
import online.market.service.common.CityService;
import online.market.service.common.CountryService;
import online.market.service.common.EmailService;
import online.market.service.entity.CustomerService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class LoginController
{
    private final CustomerService customerService;
    private final CountryService countryService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final CityService cityService;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("classActiveMyAccount", "home active");
        CustomerDto customerDto = new CustomerDto();
        model.addAttribute("customerRegistration", customerDto);
        return "/auth/login";
    }

    @PostMapping("/login-second")
    public String signInPage(@ModelAttribute("customerRegistration") CustomerDto customerDto,
                             @RequestParam(name = "username") String username,
                             @RequestParam(name = "password") String password,
                             HttpServletRequest request, Model model)
    {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Customer customer=customerService.findByUsername(username);
            request.getSession().setAttribute("customer", customer);
            System.out.println(customer);
            return "redirect:/";
        }
        catch (AuthenticationException e)
        {
            model.addAttribute("classActiveMyAccount", "home active");
            model.addAttribute("customerRegistration", customerDto);
            model.addAttribute("error", "Invalid username or password");
            return "/auth/login";
        }
    }
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login?logout";
    }


    @GetMapping("/forget-password")
    public String forgetPassword(Model model) {
        model.addAttribute("classActiveMyAccount", "home active");
        CustomerDto customerDto = new CustomerDto();
        model.addAttribute("customerRegistration", customerDto);
        return "";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("classActiveMyAccount", "home active");
        CustomerDto customerDto = new CustomerDto();
        model.addAttribute("customerRegistration", customerDto);
        return "/auth/register";
    }
    @PostMapping("/register")
    public String registerNewAccount(@ModelAttribute("customerRegistration") CustomerDto customerDto,
                                     BindingResult bindingResult,
                                     HttpServletRequest request, Model model)
    {
        model.addAttribute("classActiveMyAccount", "home active");
        model.addAttribute("customerRegistration", customerDto);
        Customer customerExists = customerService.findByUsername(customerDto.getUsername());
        if (customerExists != null) {
            System.out.println("psot1");
            return "redirect:/register?email";
        }
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        Customer customer = customerService.save(customerDto);
        String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        emailService.registration(url, customer);
        return "redirect:/register?success";
    }
}
