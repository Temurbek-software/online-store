package online.market.client.controller;


import lombok.RequiredArgsConstructor;
import online.market.model.entity.CartItem;
import online.market.model.entity.Customer;
import online.market.model.payload.CustomerDto;
import online.market.service.common.CartItemService;
import online.market.service.common.CustomerTariffService;
import online.market.service.common.ShoppingCartService;
import online.market.service.entity.CategoryService;
import online.market.service.entity.CustomerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountController {
    private final CustomerService customerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CategoryService categoryService;
    private final CartItemService cartItemService;
    private final ShoppingCartService shoppingCartService;
    private final CustomerTariffService customerTariffService;

    public void addCartAttributes(Model model, Principal principal) {
        List<CartItem> cartItemList = new ArrayList<>();
        long number = 0;
        float subtotal = 0;
        if (principal != null) {
            Customer customer = customerService.findByUsername(principal.getName());
            cartItemList = cartItemService.getCartItemsByCustomerId(customer.id);
            number = customer.id;
            subtotal = shoppingCartService.getSubTotal(customer.getShoppingCart());
        }
        model.addAttribute("cartList", cartItemList);
        model.addAttribute("counter", cartItemService.counter(number));
        model.addAttribute("subtotal", subtotal);
    }

    @RequestMapping("/my-account")
    public String myAccount(Model model, Principal principal) {
        addCartAttributes(model, principal);
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
        model.addAttribute("currentTariffList",customerTariffService.customerTariffList(customer.getId()));
        model.addAttribute("historyTariff",customerTariffService.customerTariffsHistory(customer.getId()));
        return "/userAccount/UserAccount";
    }
}
