package online.market.client.controller;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.*;
import online.market.service.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController
{
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;
    private final PublisherService publisherService;
    private final CompanyService companyService;
    private final AuthorService authorService;

    @RequestMapping("/")
    public String getHomePage(Model model)
    {
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        model.addAttribute("listOfSubCategory",subCategoryService.getSubCategoryById(categoryService.categoryList(false).get(0).getId()));
        model.addAttribute("subcategory", subCategoryService.getAllSubCategories(false));
        model.addAttribute("bookList", productService.productDtoList(false));
        model.addAttribute("authors", authorService.authorDtoList(false));
        return "home/homePage";
    }

    @RequestMapping("/bookList-category")
    public String getTotalDataByCategory(@RequestParam("id") Long id,Model model)
    {
        Category category= categoryService.categoryById(id);
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        model.addAttribute("productListByCategory",productService.productListBySubCategoryId(id,category));
        return "client/product-grid";
    }
    @RequestMapping("/bookList-subcategory")
    public String getTotalDataBySubCategory(@RequestParam("id") Long id,Model model)
    {
        SubCategory subCategory=subCategoryService.getOneItemById(id);
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        model.addAttribute("productListByCategory",productService.productListBySubCategoryId(id,subCategory));
        return "client/product-grid";
    }

    @RequestMapping("/book-details")
    public String getOneBookPage(@RequestParam("id") Long id, Model model) {
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        Product product = productService.getOneProductDto(id);
        try {
            //Get product
            model.addAttribute("oneProduct", product);

        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "/client/product-details";
        }
        return "/client/product-details";
    }

    @RequestMapping("/authors")
    public String getAuthorPage(Model model) {
        return findPaginated(1, model);
    }

    @GetMapping("/author/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 8;

        Page<Author> page = authorService.findPaginated(pageNo, pageSize);
        List<Author> authorList = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listOfAuthors", authorList);
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        return "model/authorsList";
    }

    @RequestMapping("/publishers")
    public String getPublisherPage(Model model) {
        return findPaginatedPublisher(1, model);

    }

    @RequestMapping("publisher/{pageNo}")
    public String findPaginatedPublisher(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize2 = 6;
        Page<Publisher> page = publisherService.findPaginatedPublisher(pageNo, pageSize2);
        List<Publisher> publisherList = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("publisherList", publisherList);
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        return "model/publishersList";
    }

    @RequestMapping("/companies")
    public String getCompanyPage(Model model) {
        return findPaginatedCompany(1, model);
    }

    @RequestMapping("/company/{pageNo}")
    public String findPaginatedCompany(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize3 = 6;
        Page<Company> page = companyService.findPaginatedCompany(pageNo, pageSize3);
        List<Company> companyList = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("companyList", companyList);
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        return "model/companyList";
    }


    @RequestMapping("/contactUs")
    public String getContactPage(Model model) {
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        return "pages/contactUS";
    }

    @RequestMapping("/aboutUs")
    public String getAboutPage(Model model) {
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        return "pages/aboutUS";
    }
}
