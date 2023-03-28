package online.market.service.common;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.CartItem;
import online.market.model.entity.Customer;
import online.market.model.entity.Product;
import online.market.model.entity.ShoppingCart;
import online.market.model.repository.CartItemRepository;
import online.market.model.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    private Float TAX_RATE = 5.0F; //5% Percent

    public ShoppingCart findShoppingCart(Customer customer) {
        if (customer.getShoppingCart() == null) {
            ShoppingCart shoppingCart = new ShoppingCart();

            shoppingCart.setPaymentMethod("Cash On Delivery");
            shoppingCart.setShippingMethod("Free Shipping");

            shoppingCart.setCustomer(customer);
            shoppingCart.setCartItemList(new ArrayList<CartItem>());

            return shoppingCart;
        }
        return customer.getShoppingCart();
    }

    public ShoppingCart findById(Long shoppingCartId) {
        return shoppingCartRepository.findById(shoppingCartId).get();
    }

    private Float getSubTotal(ShoppingCart shoppingCart) {
        Float subTotal = 0.0F;
        for (CartItem cartItem : shoppingCart.getCartItemList()) {
            subTotal += cartItem.getTotalPrice();
        }
        return subTotal;
    }

    public ShoppingCart addItemToCart(Product product, Customer customer, Long quantity) {
        return null;
    }

    public void emptyShoppingCart(Customer customer) {
        if (customer == null || customer.getShoppingCart() == null) return;

        shoppingCartRepository.delete(customer.getShoppingCart());
    }
}
