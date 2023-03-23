package online.market.service.entity;


import online.market.model.entity.Company;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @apiNote  Extra and CRUD services for entity {@link Company}
 * @author Temurbek
 * @version 1.0
 */
public interface CompanyService {
    List<Company> getAllCompanyDTO(boolean info);
    Company getCompanyDTOById(long  id);
    void insertNewCompany(Company companyDTO);
    void updateCompanyWithNewOne(long id,Company company);
    Page<Company> findPaginatedCompany(int pageNo, int pageSize);
    boolean deleteCompanyById(long id);
}
