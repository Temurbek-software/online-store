package online.market.service.entity;

import online.market.model.entity.SubCategory;

import java.util.List;

/**
 * @apiNote  Extra and CRUD services for entity {@link SubCategory}
 * @author Temurbek
 * @version 1.0
 */
public interface SubCategoryService
{
    List<SubCategory> getAllSubCategories(boolean info);
    List<SubCategory> getSubCategoryById(long subId);
    SubCategory getOneItemById(long id);
    boolean deleteCategoryDTO(long id);
    void updateSubCategory(long id, SubCategory subCategory);
    boolean saveSubCategoryDTO(SubCategory category);

}
