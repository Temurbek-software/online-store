package online.market.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.market.model.entity.template.BaseEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="orders")
public class Order extends BaseEntity
{

    //------------ Mapped Column -----------//
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id",nullable = false, referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Customer customer;
    //-------------------------------------//

    @Column(name ="order_date")
    private Date orderDate;

    @Column(name ="delivery_date")
    private Date deliveryDate;

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

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetail> orderDetailList;


}
