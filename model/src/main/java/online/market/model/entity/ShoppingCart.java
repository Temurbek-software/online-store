package online.market.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.market.model.entity.template.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="shopping_cart")
public class ShoppingCart extends BaseEntity {

    @Column(name = "sub_total")
    private Float subTotal;

    @Column(name = "shipping_total")
    private Float shippingTotal;

    //Default 5 %
    @Column(name = "tax_rate")
    private Float taxRate;

    @Column(name = "tax_total")
    private Float taxTotal;

    @Column(name = "grand_total")
    private Float grandTotal;

    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "description")
    private String description;

    //---Mapped column----//
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    //-------------------//

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shoppingCart")
    private List<CartItem> cartItemList;
}
