package online.market.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import online.market.model.entity.template.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="shoppingCart")
public class ShoppingCart extends BaseEntity {

    @Column(name = "sub_total")
    private Float subTotal;

    @Column(name = "shipping_total")
    private Float shippingTotal;

    @Column(name = "tax_total")
    private Float totalAmount;

    @Column(name = "discount_percent")
    private Integer discount_percent;

    @Column(name = "discount_Code")
    private String discount_code;

    @Column(name = "discount_amount")
    private Double discount_amount;

    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "status")
    private boolean status;

    @Column(name = "description",columnDefinition = "text")
    private String description;

    //---Mapped column----//
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonIgnore
    private Customer customer;
    //-------------------//

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shoppingCarts")
    private List<CartItem> cartItemList;
}
