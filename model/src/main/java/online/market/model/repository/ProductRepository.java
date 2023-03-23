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
}
