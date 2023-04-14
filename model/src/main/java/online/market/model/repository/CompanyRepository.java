package online.market.model.repository;

import online.market.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query(value = "select * from company s where s.is_deleted=:deleted", nativeQuery = true)
    List<Company> getAllCompanyIfNotDeleted(boolean deleted);

    Company findCompanyById(long id);

    @Query(value = "SELECT * FROM company s WHERE extract (YEAR from s.created_at)=:year limit 3", nativeQuery = true)
    List<Company> getCompanyByCreatedAt(int year);

    @Query(value = "SELECT category.id, category.name, COUNT(product.id) as product_count " +
            "FROM category " +
            "LEFT JOIN product ON category.id = product.category_id " +
            "GROUP BY category.id, category.name " +
            "ORDER BY category.name", nativeQuery = true)
    List<Object[]> getCategoryProductCount();
}
