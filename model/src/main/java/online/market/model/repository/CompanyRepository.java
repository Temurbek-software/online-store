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
}
