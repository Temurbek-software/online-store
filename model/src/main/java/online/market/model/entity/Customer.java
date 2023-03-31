package online.market.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.market.model.entity.template.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer",uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class Customer extends BaseEntity {
    @NotEmpty(message = "First name can't be empty!")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Last name can't be empty!")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String username;

    @NotNull(message = "Enter phoneNumber!")
    @Column(name = "phoneNumber")
    private String phoneNumber;

    @NotNull(message = "Enter password!")
    @Column(name = "password")
    private String password;


    @NotNull(message = "Enter country!")
    @Column(name = "countryName")
    private String countryName;
    //Address info
    @NotNull(message = "Enter postal code!")
    @Column(name = "postal_code")
    private String postalCode;

    @NotNull(message = "Enter city Name !")
    @Column(name = "cityName")
    private String cityName;


    @NotNull(message = "Enter address !")
    @Column(name = "address")
    private String address;
    //---------------- end address -------------//

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "customer_roles",
            joinColumns = @JoinColumn(
                    name = "customer_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
