package online.market.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.market.model.entity.template.BaseEntity;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cart_item")
public class CartItem extends BaseEntity {

    //--------------------------------------//
    @Column(name = "quantity")
    private Long quantity=0L;

    @Column(name = "total_price")
    private Float totalPrice=0.0F;

    @Column(name = "format")
    private String book_format;

    @Column(name = "in_Stock")
    private boolean in_stock;

    //------------ Mapped Column -----------//
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shopping_cart_id", referencedColumnName = "id")
    private ShoppingCart shoppingCarts;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
