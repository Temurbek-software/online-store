package online.market.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.market.model.entity.template.BaseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company extends BaseEntity {
    @Column(name = "nameOfCompany")
    @NotNull
    private String nameOfCompany;

    @Column(name = "address")
    @NotNull
    private String address;

    @Column(name = "authorOfCompany")
    private String authorForCompany;

    @Column(name = "phoneNumber")
    @NotNull
    private String phoneNumber;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "yearOfPublished")
    @NotNull
    private Date yearOfPublished;

    @Column(name = "description")
    @NotNull
    private String description;

    @Transient
    private MultipartFile image_posted1;

    @Column(name = "image1")
    private String image1;

    @OneToMany(fetch = FetchType.LAZY,
            orphanRemoval = true, mappedBy = "productCompany")
    @JsonIgnore
    private Set<Product> companyProduct = new HashSet<>();

    public String getFullImage1Url() {
        if (id != null && image1 != null) {
            return "/upload/company/" + id + "/" + image1;
        } else {
            return "/upload/company/company.jpg";
        }
    }
}
