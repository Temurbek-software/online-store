package online.market.admin.controller;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Category;
import online.market.service.entity.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    private static final String redirect = "redirect:/category/list";
    private static final String redirectAfterDeleted = "redirect:/category/list?deleted";
    private static final String redirectAfterAdded = "redirect:/category/list?success";
    private static final String add_edit = "model/category/addCategory";

    @GetMapping("/list")
    public String showAllCategory(Model model) {
        model.addAttribute("allCategory", categoryService.categoryList(false));
        return "model/category/categoryList";
    }

    @GetMapping("/edit/{id}")
    public String updateCategoryWith(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.categoryById(id);
        model.addAttribute("categoryNewData",category);
        return add_edit;
    }

    //    Deleted list done by admin or moderator
    @GetMapping("/delete/{id}")
    public String getDeletedCategory(@PathVariable("id") Long id, Model model) {
        model.addAttribute("deletedMsg", categoryService.deleteCategoryDTO(id));
        return redirectAfterDeleted;
    }

    //    Adding new element for Author
    @GetMapping("/add")
    public String getSavePage(@ModelAttribute("categoryNewData") Category categoryDTO, Model model) {
        model.addAttribute("categoryNewData", categoryDTO);
        return add_edit;
    }

    @PostMapping("/save")
    public String saveNewCategory(@ModelAttribute("categoryNewData") Category categoryDTO,
                                  BindingResult result,
                                  Model model) {
        model.addAttribute("categoryNewData",categoryDTO);
        model.addAttribute("allCategory", categoryService.categoryList(false));
        if (result.hasErrors()) {
            return "model/category/addCategory";
        }
        if (categoryDTO.id==null)
        {
            categoryService.insertByCategoryDTO(categoryDTO);
        }
        else
        {
            categoryService.updateCategoryDTOById(categoryDTO.id,categoryDTO);
        }
        return redirectAfterAdded;
    }
}
