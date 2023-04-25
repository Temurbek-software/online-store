package online.market.admin.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.market.model.entity.Author;
import online.market.model.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContractWrapper
{
    private Long id;
    private Double outcome;
    private Date date_contact;
    private String notes;
    private MultipartFile image_posted1;
    private String imageData;
    private String[] productDTOSet;
    private Author author;


    public String getFullImage1Url() {
        if (id != null && imageData != null) {
            return "/upload/docs/" + id + "/" + imageData;
        } else {
            return "/upload/no_preview.jpg";
        }
    }
}
