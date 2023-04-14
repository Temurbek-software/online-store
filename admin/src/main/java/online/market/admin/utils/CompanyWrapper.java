package online.market.admin.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyWrapper {
    private String nameOfCompany;
    private String address;
    private String phoneNumber;
    private String email;
    private Date yearOfPublished;
    private String description;
    private String authorForCompany;
    private String image1;
    private MultipartFile image_posted1;

    private String[] companyProduct ;
}
