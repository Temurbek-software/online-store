package online.market.admin.controller;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Company;
import online.market.service.entity.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;
    private static final String redirect = "redirect:/company/list";
    private static final String redirectAfterDeleted = "redirect:/company/list?deleted";
    private static final String redirectAfterAdded = "redirect:/company/list?success";
    private static final String add_edit = "model/company/addCompany";

    @GetMapping("/list")
    public String getAllCompanies(Model model) {
        model.addAttribute("companyList", companyService.getAllCompanyDTO(false));
        return "model/company/companyList";
    }

    //    Deleted list done by admin or moderator
    @GetMapping("/delete/{id}")
    public String getDeletedCompany(@PathVariable("id") Long id, Model model) {
        model.addAttribute("deleteMsg", companyService.deleteCompanyById(id));
        return redirectAfterDeleted;
    }

    @GetMapping("/edit/{id}")
    public String updateCompanyWithNewItems(@PathVariable("id") Long id, Model model) {
        Company company = companyService.getCompanyDTOById(id);
        model.addAttribute("companyDTO", company);
        return add_edit;
    }

    //    Adding new element for Author
    @GetMapping("/add")
    public String getSavingPage(@ModelAttribute("companyDTO") Company companyDTO, Model model) {
        model.addAttribute("companyDTO", companyDTO);
        return add_edit;
    }

    @PostMapping("/save")
    public String insertNewProduct(@ModelAttribute("companyDTO") Company companyDTO,
                                   BindingResult result,
                                   Model model) {
        model.addAttribute("companyDTO", companyDTO);
        model.addAttribute("companyList", companyService.getAllCompanyDTO(false));
        if (result.hasErrors()) {
            return "model/subcategory/addSubcategory";
        }
        if (companyDTO.id == null) {
            companyService.insertNewCompany(companyDTO);
        } else {
            companyService.updateCompanyWithNewOne(companyDTO.id, companyDTO);
        }
        return redirectAfterAdded;
    }
}
