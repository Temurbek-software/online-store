package online.market.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.market.model.entity.template.BaseEntity;
import online.market.model.enums.Duration;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tariffs")
public class Tariffs extends BaseEntity {
    @Column(name = "tariffName")
    private String tariffName;

    @Column(name = "priceFor")
    private Double priceFor;

    @Column(name = "Duration")
    private Duration duration;

    @Column(name = "discount")
    private double discount;

    @Column(name = "start_date")
    private Date start_date;

    @Column(name = "end_date")
    private Date end_date;

    @Column(name = "availability")
    private Boolean availability;

    @Column(name = "renewal")
    private Boolean renewal;

    @Column(name = "term_and_condition")
    private String term_And_Condition;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "tariffs", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Customer> customerSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_tariff",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tariff_id"))
    @JsonIgnore
    private Set<Product> products = new HashSet<>();


}
