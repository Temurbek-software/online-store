package online.market.client.controller;

import com.itextpdf.text.pdf.PdfReader;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import online.market.model.entity.*;
import online.market.service.entity.*;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;
    private final PublisherService publisherService;
    private final CompanyService companyService;
    private final AuthorService authorService;
    private final AdvertisementService advertisementService;
    private final TariffServices tariffServices;

    @RequestMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        model.addAttribute("listOfSubCategory", subCategoryService.getSubCategoryById(categoryService.categoryList(false).get(0).getId()));
        model.addAttribute("subcategory", subCategoryService.getAllSubCategories(false));
        model.addAttribute("bookList", productService.productDtoList(false));
        model.addAttribute("authors", authorService.authorDtoList(false));
        model.addAttribute("ads", advertisementService.findAllItems());
        model.addAttribute("weekBookList", productService.productWeekList());
        model.addAttribute("tariffList", tariffServices.getAllTariffs());
        return "home/homePage";
    }

    @RequestMapping("/reading")
    public String getBooksReading(@RequestParam("id") Long id, Model model) {
        Product product = productService.getOneProductDto(id);
        System.out.println(product.getFullPdf());
        model.addAttribute("product", product);
        return "/client/readingPdf";
    }

    //    @GetMapping("/reading")
//    public String showBook(@RequestParam("id") Long id,Model model, @RequestParam(name = "page", defaultValue = "1") int page) throws IOException {
//        Product product = productService.getOneProductDto(id);
//
//        // Read book content from file
//
//        Path filePath = Paths.get("http://localhost:81/admin"+product.getFullPdf());
//
//        List<String> bookLines = Files.readAllLines(filePath);
//
//        // Calculate starting line and ending line of the requested page
//        int linesPerPage = 20;
//        int startingLine = (page - 1) * linesPerPage;
//        int endingLine = Math.min(startingLine + linesPerPage, bookLines.size());
//
//        // Create a substring of the book content for the requested page
//        String bookContent = String.join("\n", bookLines.subList(startingLine, endingLine));
//
//        // Add book content and page number to the model
//        model.addAttribute("bookContent", bookContent);
//        model.addAttribute("page", page);
//
//        // Return the book.html template
//        return "client/readingPdf";
//    }
//    @GetMapping("/reading")
//    public String showBook(@RequestParam("id") Long id, Model model, @RequestParam(name = "page", defaultValue = "1") int page) throws IOException {
//        Product product = productService.getOneProductDto(id);
//        String bookUrl = "http://localhost:81/admin" + product.getFullPdf();
//
//        // Make an HTTP request to the bookUrl to get the book content
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(bookUrl).build();
//        Response response = client.newCall(request).execute();
//        String bookContent = response.body().string();
//
//        // Calculate starting line and ending line of the requested page
//        int linesPerPage = 20;
//        int startingLine = (page - 1) * linesPerPage;
//        int endingLine = Math.min(startingLine + linesPerPage, bookContent.length());
//
//        // Create a substring of the book content for the requested page
//        String pageContent = bookContent.substring(startingLine, endingLine);
//
//        // Add book content, page number and total number of pages to the model
//        int totalPages = (int) Math.ceil((double) bookContent.length() / linesPerPage);
//        model.addAttribute("bookContent", pageContent);
//        model.addAttribute("page", page);
//        model.addAttribute("totalPages", totalPages);
//
//        // Return the book.html template
//        return "client/readingPdf";
//    }
    @GetMapping("/reading")
    public String showBook(@RequestParam("id") Long id,Model model) throws IOException {
        Product product = productService.getOneProductDto(id);
        String bookUrl = "http://localhost:81/admin" + product.getFullPdf();
        System.out.println(bookUrl);
        // Read the contents of the PDF file into a byte array
        byte[] bookContent = IOUtils.toByteArray(new URL(bookUrl));
        System.out.println(bookContent.toString());
        // Pass the book content to the view
        model.addAttribute("bookContent", bookUrl);
        model.addAttribute("bookType", "application/pdf");

        return "client/readingPdf";
    }

    @RequestMapping("/bookList-category")
    public String getTotalDataByCategory(@RequestParam("id") Long id, Model model) {
        Category category = categoryService.categoryById(id);
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        model.addAttribute("productListByCategory", productService.productListBySubCategoryId(id, category));
        return "client/product-grid";
    }

    @RequestMapping("/bookList-subcategory")
    public String getTotalDataBySubCategory(@RequestParam("id") Long id, Model model) {
        SubCategory subCategory = subCategoryService.getOneItemById(id);
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        model.addAttribute("productListByCategory", productService.productListBySubCategoryId(id, subCategory));
        return "client/product-grid";
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
