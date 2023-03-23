package online.market.admin.controller;

import lombok.RequiredArgsConstructor;
import online.market.service.entity.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class DashboardController {
    private final ProductService productService;

    @GetMapping()
    public String getHomePage(Model model) {

        return "dashboard/index";
    }
}
