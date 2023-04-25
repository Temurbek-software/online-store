package online.market.client.controller;


import lombok.RequiredArgsConstructor;
import online.market.model.entity.Customer;
import online.market.model.payload.CustomerDto;
import online.market.service.common.OrderService;
import online.market.service.entity.CategoryService;
import online.market.service.entity.CustomerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final CustomerService customerService;
    private final OrderService orderService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CategoryService categoryService;

    @RequestMapping("/my-account")
    public String myAccount(Model model)
    {
        model.addAttribute("classActiveMyAccount", "home active");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
//      Get logged in customer
        Customer customer = customerService.findByUsername(currentUserName);
        CustomerDto customerDto=new CustomerDto();
        customerDto.setAddress(customer.getAddress());
        customerDto.setCityName(customer.getCityName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setPassword(customer.getPassword());
        System.out.println(customer.getPassword());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setPostalCode(customer.getPostalCode());
        customerDto.setCountryName(customer.getCountryName());
        customerDto.setUsername(customer.getUsername());
        model.addAttribute("customer", customerDto);
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());

        return "/userAccount/UserAccount";
    }
}
