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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product extends BaseEntity {

    @Column(name = "book_name")
    @NotNull
    private String bookName;

    @Column(name = "electronic_price")
    @NotNull
    private Double e_price;

    @Column(name = "printed_price")
    @NotNull
    private Double printed_Price;

    @Column(name = "audio_price")
    @NotNull
    private Double audio_price;

    @Column(name = "yearOfPublished")
    @NotNull
    private Date yearOfPublished;

    @Column(name = "pageNumber")
    @NotNull
    private Integer pageNumb;

    @Column(name = "description")
    @NotNull
    private String description;

    @Column(name = "Language")
    @NotNull
    private String language;

    @Column(name = "ISBNnumber")
    @NotNull
    private String isbnNumber;

    //    image param
    @Column(name = "image")
    private String imageData;

    @Transient
    private MultipartFile image_posted1;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category categoryItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id",referencedColumnName = "id")
    private SubCategory subCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_Id", referencedColumnName = "id")
    private Company productCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_Id", referencedColumnName = "id")
    private Publisher productPublisherList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_author",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @JsonIgnore
    private Set<Author> productAuthorList = new HashSet<>();



    public void addAuthor(Author author) {
        this.productAuthorList.add(author);
        author.getProductDTOSet().add(this);
    }

    public void removeAuthor(Author author) {
        this.getProductAuthorList().remove(author);
        author.getProductDTOSet().remove(this);
    }

    public void removeAuthors()
    {
        for (Author author : new HashSet<>(productAuthorList)) {
            removeAuthor(author);
        }
    }
    public String getFullImage1Url() {
        if (id != null && imageData != null) {
            return "/upload/product/" + id + "/" + imageData;
        } else {
            return "/upload/no_preview.jpg";
        }
    }

}
