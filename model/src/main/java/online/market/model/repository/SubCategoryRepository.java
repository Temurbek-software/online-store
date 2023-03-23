package online.market.model.repository;

import online.market.model.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    @Query(value = "SELECT s.id, s.name FROM sub_category s " +
            "inner join category d on d.category_id=s.id where d.id=:id", nativeQuery = true)
    List<SubCategory> getSubCategoriesByCategoryId(long id);

    @Query(value = "select * from subcategory s where s.is_deleted=:deleted", nativeQuery = true)
    List<SubCategory> getAllSubCategoriesIfDeleted(boolean deleted);

    SubCategory findSubCategoryById(long id);

    @Query(value = "select * from subcategory s where s.category_id=:id",nativeQuery = true)
    List<SubCategory> getSubCategoryByCategoryId(long id);



}
