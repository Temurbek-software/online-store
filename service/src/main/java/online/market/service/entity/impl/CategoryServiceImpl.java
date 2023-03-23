package online.market.service.entity.impl;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Category;
import online.market.model.repository.CategoryRepository;
import online.market.service.entity.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;





    @Override
    public List<Category> categoryList(boolean data) {
        List<Category> categoryDTOList = categoryRepository.getCategoriesByDeletedOrDeletedNot(data);
        return categoryDTOList;
    }

    @Override
    public List<Category> getAllCategoryWithSubCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category categoryById(long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public void insertByCategoryDTO(Category categoryDTO) {
        categoryRepository.save(categoryDTO);
    }

    @Override
    public boolean deleteCategoryDTO(long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void updateCategoryDTOById(long id, Category categoryDTO) {
        categoryRepository.save(categoryDTO);
    }

}
