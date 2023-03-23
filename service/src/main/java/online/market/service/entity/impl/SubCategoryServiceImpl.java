package online.market.service.entity.impl;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Category;
import online.market.model.entity.SubCategory;
import online.market.model.repository.CategoryRepository;
import online.market.model.repository.SubCategoryRepository;
import online.market.service.entity.SubCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository categoryRepository;
    private final CategoryRepository categoryRepo;

    @Override
    public List<SubCategory> getSubCategoryById(long subId)
    {
        return categoryRepository.getSubCategoryByCategoryId(subId);
    }

    @Override
    public List<SubCategory> getAllSubCategories(boolean data) {
        List<SubCategory> allSubCategoriesIfDeleted = categoryRepository.getAllSubCategoriesIfDeleted(data);
        return allSubCategoriesIfDeleted;
    }

    @Override
    public SubCategory getOneItemById(long id) {
        return (categoryRepository.findById(id).get());
    }

    @Override
    public boolean deleteCategoryDTO(long id) {
        Optional<SubCategory> subCategory=categoryRepository.findById(id);
        if (subCategory.isPresent()){
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void updateSubCategory(long id, SubCategory subCategory)
    {
      Category category=categoryRepo.findCategoryById(subCategory.getSubcategory().id);
      subCategory.setSubcategory(category);
      categoryRepository.save(subCategory);
    }

    @Override
    public boolean saveSubCategoryDTO(SubCategory category) {
        long categoryId = category.getSubcategory().getId();
        Category category1 = categoryRepo.findCategoryById(categoryId);
        category.setSubcategory(category1);
        categoryRepository.save(category);
        return true;
    }

}
