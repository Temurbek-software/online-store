package online.market.model.repository;

import online.market.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "select * from category s where s.is_deleted=:deleted", nativeQuery = true)
    List<Category> getCategoriesByDeletedOrDeletedNot(boolean deleted);

    Category findCategoryById(long id);
}
