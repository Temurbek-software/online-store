package online.market.admin.controller;

import lombok.RequiredArgsConstructor;
import online.market.admin.utils.AuthorWrapper;
import online.market.model.entity.Author;
import online.market.model.entity.Product;
import online.market.service.entity.AuthorService;
import online.market.service.entity.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;
    private final ProductService productService;
    private static final String redirect = "redirect:/author/list";
    private static final String redirectAfterDeleted = "redirect:/author/list?deleted";
    private static final String redirectAfterAdded = "redirect:/author/list?success";
    private static final String add_edit = "model/author/addAuthor";

    //  Get all Authors list
    @GetMapping("/list")
    public String getAllAuthorList(Model model) {
        model.addAttribute("authorList", authorService.authorDtoList(false));
        return "model/author/authorList";
    }

    //  Deleted list done by admin or moderator
    @GetMapping("/delete/{id}")
    public String deleteAuthorsByID(@PathVariable("id") Long id, Model model) {
        model.addAttribute("deleteMessage", authorService.deleteAuthorById(id));
        return redirectAfterDeleted;
    }

    //  Adding new element for Author
    @GetMapping("/add")
    public String getSavePage(Model model,
                              @ModelAttribute("authorDtoData") AuthorWrapper authorWrapper) {
        model.addAttribute("productList", productService.productDtoList(false));
        return add_edit;
    }

    //    Edit Author by ID
    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable("id") Long id, Model model) {
        Author author = authorService.getOneAuthorDto(id);
        AuthorWrapper authorWrapper1 = new AuthorWrapper();
        authorWrapper1.setId(author.getId());
        authorWrapper1.setImage1(author.getImage1());
        authorWrapper1.setDescription(author.getDescription());
        authorWrapper1.setEmail(author.getEmail());
        authorWrapper1.setPhoneNumber(author.getPhoneNumber());
        authorWrapper1.setLastName(author.getLastName());
        authorWrapper1.setFirstName(author.getFirstName());
        authorWrapper1.setShortDescription(author.getShortDescription());
        authorWrapper1.setImage_posted1(author.getImage_posted1());
        String[] strings = new String[author.getProductDTOSet().size()];
        int i = 0;
        for (Product product : author.getProductDTOSet()) {
            strings[i] = String.valueOf(product.getId());
            i++;
        }
        authorWrapper1.setProductDTOSet(strings);
        model.addAttribute("authorDtoData", authorWrapper1);
        model.addAttribute("productList", productService.productDtoList(false));
        return add_edit;
    }

    //    Delete Author by ID
    @PostMapping("/save")
    public String insertNewPage(@ModelAttribute("authorDtoData") AuthorWrapper authorWrapper,
                                Model model, BindingResult result) throws IOException {
        model.addAttribute("authorDtoData", authorWrapper);
        model.addAttribute("productList", productService.productDtoList(false));
        if (result.hasErrors()) {
            return "model/author/addAuthor";
        }
        Author authorDTO = new Author();
        Set<Product> productDTOSet = new HashSet<>();
        for (String s : authorWrapper.getProductDTOSet()) {
            long authorId=Long.parseLong(s);
            productDTOSet.add(productService.getOneProductDto(authorId));
        }
        if (authorWrapper.getId() != null) {
            authorDTO.setId(authorWrapper.getId());
        }
        authorDTO.setFirstName(authorWrapper.getFirstName());
        authorDTO.setPhoneNumber(authorWrapper.getPhoneNumber());
        authorDTO.setEmail(authorWrapper.getEmail());
        authorDTO.setShortDescription(authorWrapper.getShortDescription());
        authorDTO.setLastName(authorWrapper.getLastName());
        authorDTO.setDescription(authorWrapper.getDescription());

        authorDTO.setImage1(authorWrapper.getImage1());
        authorDTO.setImage_posted1(authorWrapper.getImage_posted1());

        authorDTO.setProductDTOSet(productDTOSet);

        if (authorDTO.id != null) {
            authorService.updateAuthorById(authorWrapper.getId(), authorDTO);
        } else {
            authorService.saveAuthor(authorDTO);
        }

        return redirectAfterAdded;
    }

}
