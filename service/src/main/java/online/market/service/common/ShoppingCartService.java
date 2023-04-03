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
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    private Float SHIPPING_PRICE = 5.0F; //5% Percent

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

    private CartItem findCartItem(ShoppingCart shoppingCart, Long productId) {

        System.out.println("Cart item count" + shoppingCart.getCartItemList().stream().count());

        for (CartItem cartItem : shoppingCart.getCartItemList()) {
            //item found
            if (cartItem.getProduct().getId().equals(productId)) {
                return cartItem;
            }
        }
        return null;
    }

    public ShoppingCart addItemToCart(Product product, Customer customer, Long quantity, String format) {
        Float subTotal = 0.0F;
        double totalPrice = 0.0F;
        long totalQty = 0;
        ShoppingCart shoppingCart = findShoppingCart(customer);
        CartItem cartItem = findCartItem(shoppingCart, product.getId());
        if (cartItem == null) {
            cartItem = new CartItem();

            cartItem.setProduct(product);
            cartItem.setShoppingCarts(shoppingCart);

            //get items list and item in it
            List<CartItem> cartItemList = shoppingCart.getCartItemList();
            cartItemList.add(cartItem);

            shoppingCart.setCartItemList(cartItemList);
        }
        cartItem.setIn_stock(true);

        cartItem.setBook_format(format);
        totalQty = cartItem.getQuantity() + quantity;
        cartItem.setQuantity(totalQty);
        if (format == "Printed") {
            cartItem.setTotalPrice((float) (quantity * product.getPrinted_Price()));
            //total
            totalPrice = product.getPrinted_Price() * totalQty;
            cartItem.setTotalPrice((float) totalPrice);
        }
        if (format == "Audio") {
            cartItem.setTotalPrice((float) (quantity * product.getAudio_price()));
            //total
            totalPrice = product.getAudio_price() * totalQty;
            cartItem.setTotalPrice((float) totalPrice);
        }
        if (format == "Pdf") {
            cartItem.setTotalPrice((float) (quantity * product.getE_price()));
            //total
            totalPrice = product.getE_price() * totalQty;
            cartItem.setTotalPrice((float) totalPrice);
        }
//shopping cart
        shoppingCart.setShippingTotal(SHIPPING_PRICE);
        subTotal = getSubTotal(shoppingCart);
        shoppingCart.setSubTotal(subTotal);
        shoppingCart.setTotalAmount(subTotal - SHIPPING_PRICE);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }

    public void emptyShoppingCart(Customer customer) {
        if (customer == null || customer.getShoppingCart() == null) return;

        shoppingCartRepository.delete(customer.getShoppingCart());
    }
}
