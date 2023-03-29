package online.market.client.controller;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Customer;
import online.market.model.entity.Product;
import online.market.service.common.ShoppingCartService;
import online.market.service.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;
    private final PublisherService publisherService;
    private final CompanyService companyService;
    private final AuthorService authorService;
    private final CustomerService customerService;
    private final AdvertisementService advertisementService;
    private final ShoppingCartService shoppingCartService;
    @RequestMapping("/add-to-cart")
    public String addToCart(@ModelAttribute("id") Long id, @ModelAttribute("quantity") Long quantity,Model model,Principal principal)
    {
        model.addAttribute("classActiveViewCart", "home active");
//        Finding product
        Product product=productService.getOneProductDto(id);
//        Finding customer
        Customer customer=customerService.findByUsername(principal.getName());//getting logged in user

    }
}
