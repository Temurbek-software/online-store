package online.market.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.market.model.entity.template.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category extends BaseEntity {

    @Column(name = "name")
    @NotNull
    private String name;
    @Column(name = "description")
    @NotNull
    private String description;


    @OneToMany(mappedBy = "categoryItems", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Product> categoryProduct = new HashSet<>();

    @OneToMany(mappedBy = "subcategory",
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JsonIgnore
    private Set<SubCategory> subCategories = new HashSet<>();


}
