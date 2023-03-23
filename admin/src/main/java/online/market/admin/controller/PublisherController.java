package online.market.admin.controller;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Publisher;
import online.market.service.entity.ProductService;
import online.market.service.entity.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/publisher")
public class PublisherController {
    private final PublisherService publisherService;
    private final ProductService productService;
    private static final String redirect = "redirect:/publisher/list";
    private static final String redirectAfterDeleted = "redirect:/publisher/list?deleted";
    private static final String redirectAfterAdded = "redirect:/publisher/list?success";
    private static final String add_edit = "model/publisher/addPublisher";

    @GetMapping("/list")
    public String getAllPublisher(Model model) {
        model.addAttribute("publisherList", publisherService.getAllPublisher(false));
        return "model/publisher/publisherList";
    }

    //    editing Publishers
    @GetMapping("/edit/{id}")
    public String updatePublishers(@PathVariable("id") Long id, Model model) {
        Publisher publisher = publisherService.getOnePublisher(id);
        model.addAttribute("publisherDTO", publisher);
        return add_edit;
    }

    //    Deleted list done by admin or moderator
    @GetMapping("/delete/{id}")
    public String getDeletedBooks(@PathVariable("id") Long id, Model model) {
        model.addAttribute("deleteMsg", publisherService.deletePubById(id));
        return redirectAfterDeleted;
    }

    //    Adding new element for Author
    @PostMapping("/save")
    public String insertNewProduct(@ModelAttribute("publisherDTO") Publisher publisherDTO,
                                   Model model, BindingResult result) {
        publisherService.insertNewPublisher(publisherDTO);
        model.addAttribute("publisherList", publisherService.getAllPublisher(false));
        model.addAttribute("publisherDTO", publisherDTO);
        if (result.hasErrors()) {
            return "model/publisher/addPublisher";
        }
        if (publisherDTO.id == null) {
            publisherService.insertNewPublisher(publisherDTO);
        } else {
            publisherService.updatePublisherByIdWithNewOne(publisherDTO.id, publisherDTO);
        }
        return redirectAfterAdded;
    }

    @GetMapping("/add")
    public String getCreatingPage(@ModelAttribute("publisherDTO") Publisher publisherDTO, Model model) {
        model.addAttribute("publisherDTO", publisherDTO);
        return add_edit;
    }
}
