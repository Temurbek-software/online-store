package online.market.client.controller;

import lombok.RequiredArgsConstructor;
import online.market.client.utils.ProductCart;
import online.market.model.entity.CartItem;
import online.market.model.entity.Customer;
import online.market.model.entity.Product;
import online.market.service.common.CartItemService;
import online.market.service.common.ShoppingCartService;
import online.market.service.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;
    private final PublisherService publisherService;
    private final CompanyService companyService;
    private final AuthorService authorService;
    private final AdvertisementService advertisementService;
    private final CartItemService cartItemService;
    private final ShoppingCartService shoppingCartService;
    private final CustomerService customerService;

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

    @RequestMapping("/book-details")
    public String getOneBookPage(@RequestParam("id") Long id, Model model, Principal principal) {
        addCartAttributes(model, principal);
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        ProductCart productCart = new ProductCart();
        Product product = productService.getOneProductDto(id);
        try {
            //Extra products
            model.addAttribute("productList", productService.getProductsBySubcategoryId(id));
            //Get product
            model.addAttribute("oneProduct", product);
            model.addAttribute("productCart", productCart);
            model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());

        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "/client/product-details";
        }
        return "/client/product-details";
    }


}
