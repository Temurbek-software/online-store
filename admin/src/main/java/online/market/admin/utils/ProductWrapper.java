package online.market.admin.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.market.model.entity.Category;
import online.market.model.entity.Company;
import online.market.model.entity.Publisher;
import online.market.model.entity.SubCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWrapper
{
    private Long id;
    private String bookName;
    private Double e_price;
    private MultipartFile forE_Price;
    private Double printed_Price;
    private MultipartFile for_Printed_Price;
    private Double audio_price;
    private MultipartFile for_Audio_Price;
    private Date yearOfPublished;
    private Integer pageNumb;
    private String description;
    private String language;
    private String isbnNumber;


    private String imageData;
    private MultipartFile image_posted1;

    //  connected fields
    private Category categoryItems;
    private SubCategory subCategory;
    private Company productCompany;
    private Publisher productPublisherList;

    private String[] productAuthorList;

    public String getFullImage1Url() {
        if (id != null && imageData != null) {
            return "/upload/product/" + id + "/" + imageData;
        } else {
            return "/upload/no_preview.jpg";
        }
    }

}
