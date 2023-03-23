package online.market.admin.controller;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.SubCategory;
import online.market.service.entity.CategoryService;
import online.market.service.entity.SubCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/subcategory")
public class SubCategoryController {
    private final SubCategoryService subCategoryService;
    private final CategoryService categoryService;
    private static final String redirect = "redirect:/subcategory/list";
    private static final String redirectAfterDeleted = "redirect:/subcategory/list?deleted";
    private static final String redirectAfterAdded = "redirect:/subcategory/list?success";
    private static final String add_edit = "model/subcategory/addSubcategory";

    @GetMapping("/list")
    public String getAllSubcategory(Model model) {
        model.addAttribute("subCategoryList", subCategoryService.getAllSubCategories(false));
        return "model/subcategory/subcategoryList";
    }

    @GetMapping("/edit/{id}")
    public String updateSubCategories(@PathVariable("id") Long id, Model model) {
        SubCategory subCategory = subCategoryService.getOneItemById(id);
        model.addAttribute("categoryDTO", subCategory);
        model.addAttribute("categoryList", categoryService.categoryList(false));
        return add_edit;
    }

    //    Deleted list done by admin or moderator
    @GetMapping("/delete/{id}")
    public String getDeletedSubCategory(@PathVariable("id") Long id, Model model) {
        model.addAttribute("deletedMsg", subCategoryService.deleteCategoryDTO(id));
        return redirectAfterDeleted;
    }

    //    Adding new element for Author
    @GetMapping("/add")
    public String getSubCategoryPage(Model model, @ModelAttribute("categoryDTO") SubCategory categoryDTO) {
        model.addAttribute("categoryDTO", categoryDTO);
        model.addAttribute("categoryList", categoryService.categoryList(false));
        return add_edit;
    }

    @PostMapping("/save")
    public String insertNewSubCategory(@ModelAttribute("categoryDTO") SubCategory categoryDTO,
                                       Model model, BindingResult result) {
        model.addAttribute("categoryDTO", categoryDTO);
        model.addAttribute("subCategoryList", subCategoryService.getAllSubCategories(false));
        if (result.hasErrors()) {
            return "model/subcategory/addSubcategory";
        }
        if (categoryDTO.id == null) {
            subCategoryService.saveSubCategoryDTO(categoryDTO);
        } else {
            subCategoryService.updateSubCategory(categoryDTO.id,categoryDTO);
        }
        return redirectAfterAdded;
    }
}
