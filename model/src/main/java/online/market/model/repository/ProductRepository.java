package online.market.model.repository;

import online.market.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from product s where s.is_deleted=:deleted order by s.created_at limit 6", nativeQuery = true)
    List<Product> getAllByProductsIfNotDeleted(boolean deleted);
    Product findProductById(long id);
    @Query(value = "SELECT p.* FROM product p JOIN product p2 ON p.subcategory_id = p2.subcategory_id WHERE p2.id = :id ORDER BY p.created_at ASC limit 3", nativeQuery = true)
    List<Product> findProductByCategoryItems(Long id);
    @Query(value = "SELECT * "
            + "FROM public.product "
            + "WHERE created_at BETWEEN now() - interval '7 days' AND now() "
            + "AND is_deleted = false "
            + "ORDER BY created_at DESC "
            + "LIMIT 6", nativeQuery = true)
    List<Product> findNewBooksList();


}
