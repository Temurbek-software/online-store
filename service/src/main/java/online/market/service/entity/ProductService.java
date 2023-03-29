package online.market.service.entity;

import online.market.model.entity.Product;

import java.io.IOException;
import java.util.List;

/**
 * @apiNote  Extra and CRUD services for entity Product
 * @author Temurbek
 * @version 1.0
 */
public interface ProductService
{
    List<Product> productDtoList(boolean info);
    Product getOneProductDto(long id);
    void saveNewProduct(Product productDTO) throws IOException;
    void updateProduct(long id,Product productDTO);
    boolean deleteProduct(long id);
//  get @Products by subcategoryId or categoryId
    List<Product> productListBySubCategoryId(Long id,Object o);
//  Show Product by its subcategory
    List<Product> getProductsBySubcategoryId(Long id);
}
