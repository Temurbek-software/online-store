package online.market.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.market.model.entity.template.BaseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contact")
public class Contacts extends BaseEntity
{
    @Column(name = "contactTitle")
    private String contactTitle;

    @Column(name = "date_contact")
    private Date date_contact;

    @Column(name = "notes")
    private String notes;

    @Transient
    private MultipartFile image_posted1;

    @Column(name = "image1")
    private String image1;

    @OneToMany(fetch = FetchType.LAZY,
            orphanRemoval = true, mappedBy = "contacts")
    @JsonIgnore
    private Set<Product> productSet = new HashSet<>();
}
