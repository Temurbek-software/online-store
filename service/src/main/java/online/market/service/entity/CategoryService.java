package online.market.service.entity;


import online.market.model.entity.Category;

import java.util.List;

/**
 * @apiNote  Extra and CRUD services for entity {@link Category}
 * @author Temurbek
 * @version 1.0
 */
public interface CategoryService {
    List<Category> categoryList(boolean info);
    List<Category> getAllCategoryWithSubCategory();
    Category categoryById(long id);
    void insertByCategoryDTO(Category categoryDTO);
    boolean deleteCategoryDTO(long id);
    void updateCategoryDTOById(long id,Category categoryDTO);

}
