package online.market.admin.controller;

import lombok.RequiredArgsConstructor;
import online.market.admin.utils.ProductWrapper;
import online.market.model.entity.Author;
import online.market.model.entity.Product;
import online.market.service.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final CompanyService companyService;
    private final PublisherService publisherService;
    private final SubCategoryService subCategoryService;
    private static final String redirect = "redirect:/book/list";
    private static final String redirectAfterDeleted = "redirect:/book/list?deleted";
    private static final String redirectAfterAdded = "redirect:/book/list?success";
    private static final String add_edit = "model/product/addProduct";

    // Non-deleted list from product list
    @GetMapping("/list")
    public String getHomePage(Model model) {
        model.addAttribute("productList", productService.productDtoList(false));
        return "model/product/productList";
    }


    //    Deleted list done by admin or moderator
    @GetMapping("/delete/{id}")
    public String deleteItemsByID(@PathVariable("id") Long id, Model model) {
        model.addAttribute("deleteMsg", productService.deleteProduct(id));
        return redirectAfterDeleted;
    }

    //    Adding new element for Product
    @GetMapping("/add")
    public String getInsertingPage(@ModelAttribute("productWrapper") ProductWrapper productWrapper,
                                   Model model) {
        model.addAttribute("categories", categoryService.categoryList(false));
        model.addAttribute("subcategoryList", subCategoryService.getAllSubCategories(false));
        model.addAttribute("companies", companyService.getAllCompanyDTO(false));
        model.addAttribute("publishers", publisherService.getAllPublisher(false));
        model.addAttribute("authorList", authorService.authorDtoList(false));
        return "model/product/addProduct";
    }

    //  Edit Product by ID
    @GetMapping("/edit/{id}")
    public String updateItemsById(@PathVariable("id") Long id,
                                  Model model) throws IOException {
        Product product = productService.getOneProductDto(id);
        ProductWrapper productWrapper1 = new ProductWrapper();

        productWrapper1.setId(product.getId());
        productWrapper1.setAudio_price(product.getAudio_price());
        productWrapper1.setE_price(product.getE_price());
        productWrapper1.setImage_posted1(product.getImage_posted1());
        productWrapper1.setImageData(product.getImageData());
        productWrapper1.setBookName(product.getBookName());
        productWrapper1.setLanguage(product.getLanguage());
        productWrapper1.setYearOfPublished(product.getYearOfPublished());
        productWrapper1.setPageNumb(product.getPageNumb());
        productWrapper1.setIsbnNumber(product.getIsbnNumber());
        productWrapper1.setDescription(product.getDescription());
        productWrapper1.setPrinted_Price(product.getPrinted_Price());

        productWrapper1.setProductCompany(product.getProductCompany());
        productWrapper1.setProductPublisherList(product.getProductPublisherList());
        productWrapper1.setCategoryItems(product.getCategoryItems());
        productWrapper1.setSubCategory(product.getSubCategory());

        String[] strings = new String[product.getProductAuthorList().size()];
        int i = 0;
        for (Author author : product.getProductAuthorList()) {
            strings[i] = String.valueOf(author.getId());
            i++;
        }
        productWrapper1.setProductAuthorList(strings);
        model.addAttribute("productWrapper", productWrapper1);

        model.addAttribute("subcategoryList", subCategoryService.getAllSubCategories(false));
        model.addAttribute("categories", categoryService.categoryList(false));
        model.addAttribute("companies", companyService.getAllCompanyDTO(false));
        model.addAttribute("publishers", publisherService.getAllPublisher(false));
        model.addAttribute("authorList", authorService.authorDtoList(false));

        return add_edit;
    }

    @PostMapping("/save")
    public String saveNewProduct(@ModelAttribute("productWrapper") ProductWrapper productWrapper,
                                 Model model,BindingResult result) throws IOException {

        model.addAttribute("subcategoryList", subCategoryService.getAllSubCategories(false));
        model.addAttribute("categories", categoryService.categoryList(false));
        model.addAttribute("companies", companyService.getAllCompanyDTO(false));
        model.addAttribute("publishers", publisherService.getAllPublisher(false));

        model.addAttribute("authorList", authorService.authorDtoList(false));
        if (result.hasErrors()) {
            return "model/product/addProduct";
        }
        Product productDTO = new Product();
        Set<Author> authorDTOS = new HashSet<>();
        for (String s : productWrapper.getProductAuthorList()) {
            long productId = Long.parseLong(s);
            authorDTOS.add(authorService.getOneAuthorDto(productId));
        }
        if (productWrapper.getId() != null) {
            productDTO.setId(productWrapper.getId());
        }
        productDTO.setPageNumb(productWrapper.getPageNumb());
        productDTO.setIsbnNumber(productWrapper.getIsbnNumber());
        productDTO.setLanguage(productWrapper.getLanguage());
        productDTO.setYearOfPublished(productWrapper.getYearOfPublished());
        productDTO.setAudio_price(productWrapper.getAudio_price());
        productDTO.setBookName(productWrapper.getBookName());
        productDTO.setDescription(productWrapper.getDescription());
        productDTO.setE_price(productWrapper.getE_price());
        productDTO.setPrinted_Price(productWrapper.getPrinted_Price());

        productDTO.setImage_posted1(productWrapper.getImage_posted1());
        productDTO.setImageData(productWrapper.getImageData());

        productDTO.setSubCategory(productWrapper.getSubCategory());
        productDTO.setProductPublisherList(productWrapper.getProductPublisherList());
        productDTO.setCategoryItems(productWrapper.getCategoryItems());
        productDTO.setProductCompany(productWrapper.getProductCompany());

        productDTO.setProductAuthorList(authorDTOS);
//        update book by id
        if (productDTO.id != null) {
            productService.updateProduct(productWrapper.getId(), productDTO);
        } else {
            productService.saveNewProduct(productDTO);
        }
        return redirectAfterAdded;
    }
}
