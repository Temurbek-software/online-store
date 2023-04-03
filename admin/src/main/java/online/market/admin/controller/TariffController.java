package online.market.admin.controller;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Tariffs;
import online.market.service.entity.TariffServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tariff")
public class TariffController {
    private final TariffServices tariffServices;

    @GetMapping("/list")
    public String getListOfTariff(Model model)
    {
        model.addAttribute("tariffs", tariffServices.getAllTariffs());
        return "model/tariffs/viewTariff";
    }
    @GetMapping("/add")
    public String deleteTariff(@ModelAttribute("tariff") Tariffs tariffs, Model model)
    {
        return "model/tariffs/addTariffs";
    }
    @PostMapping("/save")
    public String insertNewTariff(@ModelAttribute("tariff") Tariffs tariffs,
                                  Model model, BindingResult result)
    {
        if (result.hasErrors()) {
            return "model/tariffs/addTariffs";
        }
        tariffServices.createNewTariff(tariffs);
        return "redirect:/tariff/list?success";
    }
}
