package online.market.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.market.model.entity.template.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subcategory")
public class SubCategory extends BaseEntity {
    @Column(name = "name")
    private String categoryName;

    @ManyToOne(cascade = {
            CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_Id", referencedColumnName = "id")
    @JsonIgnore
    private Category subcategory;

}
