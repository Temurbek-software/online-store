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
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "author")
@Entity
public class Author extends BaseEntity {
    @Column(name = "firstName",unique = true)
    @NotNull
    private String firstName;

    @Column(name = "lastName")
    @NotNull
    private String lastName;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "phoneNumber")
    @NotNull
    private String phoneNumber;

    @Column(name = "description")
    @NotNull
    private String description;

    @Column(name = "shortDescription")
    @NotNull
    private String shortDescription;

   //    image param

    @Transient
    private MultipartFile image_posted1;

    @Column(name = "image1")
    private String image1;


    @ManyToMany(mappedBy = "productAuthorList")
    @JsonIgnore
    private Set<Product> productDTOSet = new HashSet<>();


    public void removeProduct(Product product) {
        this.getProductDTOSet().remove(product);
        product.getProductAuthorList().remove(this);
    }
    public void addProduct(Product product) {
        this.productDTOSet.add(product);
        product.getProductAuthorList().add(this);
    }


    public void removeAllProducts() {
        for (Product product : new HashSet<>(productDTOSet)) {
            removeProduct(product);
        }
    }

    public String getFullImage1Url() {
        if (id != null && image1 != null) {
            return "/upload/author/" + id + "/" + image1;
        } else {
            return "/upload/no_preview.jpg";
        }
    }
}
