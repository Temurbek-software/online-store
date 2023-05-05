package online.market.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.market.model.entity.template.BaseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contacts")
public class Contracts extends BaseEntity
{
    @Column(name = "outcome")
    private Double outcome;

    @Column(name = "date_contact")
    private Date date_contact;

    @Column(name = "notes")
    private String notes;

    @Transient
    private MultipartFile image_posted1;

    @Column(name = "image")
    private String imageData;

    @OneToMany(fetch = FetchType.LAZY,
            orphanRemoval = true, mappedBy = "contractsProduct")
    @JsonIgnore
    private List<Product> productSet = new ArrayList<>();

    @OneToOne(mappedBy = "contractsAuthors", cascade = CascadeType.ALL)
    private Author authorContracts;


    public String getFullImage1Url() {
        if (id != null && imageData != null) {
            return "/upload/docs/" + id + "/" + imageData;
        } else {
            return "/upload/no_preview.jpg";
        }
    }
}
