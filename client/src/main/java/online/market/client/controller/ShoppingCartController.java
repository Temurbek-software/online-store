package online.market.client.controller;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Customer;
import online.market.model.entity.Product;
import online.market.model.entity.ShoppingCart;
import online.market.service.common.ShoppingCartService;
import online.market.service.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String addToCart(@RequestParam("id") Long id,
                            @RequestParam("quantity") Long quantity,
                            @RequestParam("format") String format,
                            Model model, Principal principal) {
        model.addAttribute("classActiveViewCart", "home active");
//        Finding product
        Product product = productService.getOneProductDto(id);
//        Finding customer
        if (customerService.findByUsername(principal.getName()) == null) {
            return "/auth/login";
        }
        Customer customer = customerService.findByUsername(principal.getName());//getting logged in user
        shoppingCartService.addItemToCart(product, customer, quantity, format);
        return "redirect:/book-details?id=" + product.getId() + "&addtocart";
    }

    @RequestMapping("/cart-view")
    public String viewCart(Model model, Principal principal) {
        model.addAttribute("classActiveViewCart", "home active");
        Customer customer = customerService.findByUsername(principal.getName());//get logged in user
        ShoppingCart shoppingCart = customer.getShoppingCart();

        model.addAttribute("shoppingCart", shoppingCart);
        return "client/cart";
    }
}
