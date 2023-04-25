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
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
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

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;

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
    private final CustomerService customerService;

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

    @GetMapping("/tariff")
    public String showTariff(@RequestParam("id") Long id, Model model) {
        Tariffs tariffs = tariffServices.getOneTariff(id);
        model.addAttribute("myTariff", tariffs);
        return "/client/tariffDetails";
    }

    @GetMapping("/payment")
    public String displayPayment(@RequestParam("id") Long id, Model model, Principal principal) {
        Customer customer = customerService.findByUsername(principal.getName());//get logged in user
        model.addAttribute("customer", customer);
        return "/client/payment";
    }

    @GetMapping("/reading")
    public String showBook(@RequestParam("id") Long id, Model model, Principal principal) throws IOException {
        Product product = productService.getOneProductDto(id);
        String bookUrl = "http://localhost:81/admin" + product.getFullPdf()+"#toolbar=0";

        // Read the contents of the PDF file into a byte array
        byte[] bookContent = IOUtils.toByteArray(new URL(bookUrl));

        boolean isAuthenticated = false;
        if(principal != null) {
            isAuthenticated = (customerService.findByUsername(principal.getName()) != null);
        }

        // Load the PDF document
        PDDocument document = PDDocument.load(bookContent);
        // Get the number of pages in the document
        int numPages = document.getNumberOfPages();

        if(isAuthenticated) {
            // If authenticated, show all pages
            // do nothing
        } else {
            // If not authenticated, extract the first 5 pages and save them to a new document
            if (numPages > 5) {
                PDDocument newDocument = new PDDocument();
                for (int i = 0; i < 5; i++) {
                    PDPage page = document.getPage(i);
                    newDocument.addPage(page);
                }
                document = newDocument;
                numPages = 5; // update the number of pages to 5
            }
        }

        // Convert the document to a byte array
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.save(out);
        byte[] bookContentModified = out.toByteArray();
        document.close();

        // Encode the modified book content as a Base64 string
        String base64Content = Base64.getEncoder().encodeToString(bookContentModified);

        // Get the MIME type of the modified PDF file
        String bookType = "application/pdf";

        boolean isAuthenticated2 = (principal != null);
        model.addAttribute("isAuthenticated", isAuthenticated2);
        model.addAttribute("currentBook", product);
        // Pass the Base64-encoded book content and MIME type to the view
        model.addAttribute("bookContent", base64Content);
        model.addAttribute("bookType", bookType);
        model.addAttribute("pageNumber", numPages);

        // Add audio format to the view
        model.addAttribute("audioFile", "http://localhost:81/admin" + product.getFullAudioUrl());

        return "client/readingPdf";
    }


    @RequestMapping("/bookType")
    public String getProductType(@RequestParam("id") Long id, Model model) {
        List<Product> productList = new ArrayList<>();
        model.addAttribute("categoryOfList",categoryService.categoryList(false));
        if (id == 1) {
            productList = productService.getE_version();
            model.addAttribute("productListByCategory", productList);

            model.addAttribute("num","Elektron kitoblar");
        }
        if (id == 2) {
            productList = productService.getPrintedList();
            model.addAttribute("productListByCategory", productList);
            model.addAttribute("num","Qogozli kitoblar");
        }
        if (id == 3) {
            productList = productService.getAudioList();
            model.addAttribute("productListByCategory", productList);
            model.addAttribute("num","Audio kitoblar");
        }
        if (productList.isEmpty())
        {
            model.addAttribute("param",true);
        }
        return "/client/product-grid-type";
    }


    @RequestMapping("/bookList-category")
    public String getTotalDataByCategory(@RequestParam("id") Long id, Model model) {
        Category category = categoryService.categoryById(id);
        SubCategory subCategory = new SubCategory();
        model.addAttribute("category", category);
        model.addAttribute("subcategory", subCategory);
        model.addAttribute("categoryOfList", categoryService.categoryList(false));
        model.addAttribute("subcategoryList", subCategoryService.getAllSubCategories(false));
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        model.addAttribute("productListByCategory", productService.productListBySubCategoryId(id, category));
        return "client/product-grid";
    }

    @RequestMapping("/bookList-subcategory")
    public String getTotalDataBySubCategory(@RequestParam("id") Long id, Model model) {
        SubCategory subCategory = subCategoryService.getOneItemById(id);
        Category category = new Category();
        model.addAttribute("subcategory", subCategory);
        model.addAttribute("category", category);
        model.addAttribute("subcategoryList", subCategoryService.getAllSubCategories(false));
        model.addAttribute("categoryOfList", categoryService.categoryList(false));
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

        List<Author> authorList2 = authorService.listOfAuthorsCreatedAt(getYear());
        Page<Author> page = authorService.findPaginated(pageNo, pageSize);
        List<Author> authorList = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("authorList2", authorList2);
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
        model.addAttribute("activePublishers",publisherService.findPublisherList(getYear()));
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

    @RequestMapping("/company")
    public String getOneCompany(@RequestParam("id") Long id, Model model) {
        Company company = companyService.getCompanyDTOById(id);
        List<Company> companyList = companyService.displayCompanyWithDate(getYear());
        model.addAttribute("categoryList", categoryService.categoryList(false));
        model.addAttribute("companyList", companyList);
        model.addAttribute("OneCompany", company);
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        return "/model/currentCompany";
    }
    @RequestMapping("/publish")
    public String getOnePublisher(@RequestParam("id") Long id, Model model) {
        Publisher publisher = publisherService.getOnePublisher(id);
        model.addAttribute("publisherList", publisherService.findPublisherList(getYear()));
        model.addAttribute("publisher", publisher);
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        return "/model/currentPublisher";
    }


    @RequestMapping("/autho")
    public String getOneAuthors(@RequestParam("id") Long id, Model model) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        Author author = authorService.getOneAuthorDto(id);
        List<Author> authorList2 = authorService.listOfAuthorsCreatedAt(year);
        model.addAttribute("authorList2", authorList2);
        model.addAttribute("authors", author);
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        return "/model/currentAuthorsList";
    }

    public int getYear() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    @RequestMapping("/contactUs")
    public String getContactPage(Model model) {
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        return "pages/contactUS";
    }

    @RequestMapping("/aboutUs")
    public String getAboutPage(Model model) {
        model.addAttribute("categoryList", categoryService.getAllCategoryWithSubCategory());
        model.addAttribute("authors", authorService.authorDtoList(false));
        return "pages/aboutUS";
    }
}
