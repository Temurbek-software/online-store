package online.market.admin.controller;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Advertisement;
import online.market.service.entity.AdvertisementService;
import online.market.service.entity.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdvController {
    private final AdvertisementService advertisementService;
    private final ProductService productService;

    @GetMapping("/list")
    public String advertisementList(Model model) {
        model.addAttribute("adsList", advertisementService.findAllItems());
        advertisementService.findAllItems().forEach(
                advertisement ->
                {
                    System.out.println(advertisement.getImageData());
                    System.out.println(advertisement.getFullImage1Url());
                }

        );
        return "model/ads/advertisePage";
    }

    @GetMapping("/add")
    public String addNewAdv(@ModelAttribute("advertisement") Advertisement advertisement, Model model) {
        model.addAttribute("products", productService.productDtoList(false));
        return "model/ads/addAdv";
    }

    @PostMapping("/save")
    public String insertNewItems(@ModelAttribute("advertisement") Advertisement advertisement,
                                 Model model, BindingResult result) {
        model.addAttribute("products", productService.productDtoList(false));
        if (result.hasErrors()) {
            return "model/ads/addAdv";
        }
        advertisementService.createNew(advertisement);
        return "redirect:/ads/list?success";
    }
}
