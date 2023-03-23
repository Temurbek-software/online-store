package online.market.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.market.model.entity.template.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "publisher")
public class Publisher extends BaseEntity {

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "adress")
    @NotNull
    private String address;

    @Column(name = "phoneNumber")
    @NotNull
    private Integer phoneNumber;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "estabilishedYear")
    @NotNull
    private Date established_year;

    @Column(name = "description")
    @NotNull
    private String description;

    @OneToMany(mappedBy = "productPublisherList",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private Set<Product> publisherProduct = new HashSet<>();

}
