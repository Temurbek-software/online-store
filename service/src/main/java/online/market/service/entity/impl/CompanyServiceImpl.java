package online.market.service.entity.impl;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Company;
import online.market.model.repository.CompanyRepository;
import online.market.service.entity.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    public boolean deleteCompanyById(long id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            companyRepository.deleteById(company.get().getId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Page<Company> findPaginatedCompany(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.companyRepository.findAll(pageable);
    }

    @Override
    public List<Company> getAllCompanyDTO(boolean data) {
        List<Company> companyDTOList = companyRepository.getAllCompanyIfNotDeleted(data);
        return companyDTOList;
    }

    @Override
    public Company getCompanyDTOById(long id) {
        Company companyDTO = companyRepository.getOne(id);
        return companyDTO;
    }

    @Override
    public void updateCompanyWithNewOne(long id, Company company) {
        companyRepository.save(company);
    }

    @Override
    public void insertNewCompany(Company companyDTO) {
        companyRepository.save(companyDTO);

    }
}
