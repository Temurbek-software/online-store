package online.market.service.entity.impl;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Company;
import online.market.model.repository.CompanyRepository;
import online.market.service.entity.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
    public List<Company> displayCompanyWithDate(int year) {
       List<Company> companyList=companyRepository.getCompanyByCreatedAt(year);
        return companyList;
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
        if (companyDTO.getImage_posted1().getSize() > 0) {
            String image1 = uploadImages(companyDTO.getId(), companyDTO.getImage_posted1());
            companyDTO.setImage1(image1);
            companyRepository.save(companyDTO);
        }
    }
    private String uploadImages(Long productId, MultipartFile pro) {
        String fileName = "";

        String productFolder = "admin/src/main/resources/static/upload/company";

        //Save image
        try {
            byte[] bytes = pro.getBytes();

            //Create directory if not exists
            File file = new File(productFolder + "/" + productId);
            if (!file.exists()) {
                file.mkdirs();
            }

            fileName = pro.getName() + ".png";

            String fileWithFolderName = productFolder + "/" + productId + "/" + fileName;

            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(
                            new File(fileWithFolderName)));

            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }
}
