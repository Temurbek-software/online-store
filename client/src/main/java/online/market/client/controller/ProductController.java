package online.market.client.controller;

import lombok.RequiredArgsConstructor;
import online.market.client.utils.ProductCart;
import online.market.model.entity.Product;
import online.market.service.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping("/book-details")
    public String getOneBookPage(@RequestParam("id") Long id, Model model) {
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        ProductCart productCart=new ProductCart();
        Product product = productService.getOneProductDto(id);
        try {
            //Extra products
            model.addAttribute("productList",productService.getProductsBySubcategoryId(id));
            //Get product
            model.addAttribute("oneProduct", product);
            model.addAttribute("productCart",productCart);
            model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());

        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "/client/product-details";
        }
        return "/client/product-details";
    }


}
