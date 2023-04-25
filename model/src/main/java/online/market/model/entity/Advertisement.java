package online.market.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.market.model.entity.template.BaseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "advertisement")
public class Advertisement extends BaseEntity
{
    @Column(name = "titleForAdv")
    private String titleForAd;

    @Column(name = "desctiotion",columnDefinition = "text")
    private String description;

    @Column(name = "discount")
    private double discount;
    //    image param
    @Column(name = "image")
    private String imageData;

    @Column(name = "date_released")
    private Date date_released;

    @Transient
    private MultipartFile image_posted1;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_Id", referencedColumnName = "id")
    private Product product;
    public String getFullImage1Url() {
        if (id != null && imageData != null) {
            return "/upload/advertisement/" + id + "/" + imageData;
        } else {
            return "/upload/no_preview.jpg";
        }
    }

}
