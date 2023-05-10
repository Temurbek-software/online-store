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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;


    private Float SHIPPING_PRICE = 20000F; //5% Percent

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

    public Float getSubTotal(ShoppingCart shoppingCart) {
        Float subTotal = 0.0F;
        if (shoppingCart != null) {
            for (CartItem cartItem : shoppingCart.getCartItemList()) {
                subTotal += cartItem.getTotalPrice();
            }
        }
        return subTotal;
    }

    private List<CartItem> findCartItem(ShoppingCart shoppingCart, Long productId) {
        List<CartItem> items = new ArrayList<>();
        System.out.println("Cart item count" + shoppingCart.getCartItemList().stream().count());
        for (CartItem cartItem : shoppingCart.getCartItemList()) {
            //item found
            if (cartItem.getProduct().getId().equals(productId)) {
                items.add(cartItem);
            }
        }
        return items;
    }

    public ShoppingCart addItemToCart(Product product, Customer customer, Long quantity, String format) {
        Float subTotal = 0.0F;
        double totalPrice = 0.0F;
        long totalQty = 0;
        ShoppingCart shoppingCart = findShoppingCart(customer);
        List<CartItem> cartItem = findCartItem(shoppingCart, product.getId());
        Optional<CartItem> optionalCartItem = cartItem.stream()
                .filter(cartItem2 -> cartItem2.getBook_format().equals(format))
                .findFirst();
        CartItem cartItem1;
        if (cartItem.isEmpty()) {
            cartItem1 = new CartItem();
            cartItem1.setProduct(product);
            cartItem1.setShoppingCarts(shoppingCart);

            //get items list and item in it
            List<CartItem> cartItemList = shoppingCart.getCartItemList();
            cartItemList.add(cartItem1);
            shoppingCart.setCartItemList(cartItemList);
        } else {
            if (optionalCartItem.isPresent()) {
                cartItem1 = optionalCartItem.get();
                totalQty = cartItem1.getQuantity() + quantity;
            } else {
                cartItem1 = new CartItem();
                totalQty = quantity;
                cartItem1.setProduct(product);
                cartItem1.setShoppingCarts(shoppingCart);
                List<CartItem> cartItemList = shoppingCart.getCartItemList();
                cartItemList.add(cartItem1);
                shoppingCart.setCartItemList(cartItemList);
                cartItemRepository.save(cartItem1);
            }
        }
        cartItem1.setIn_stock(true);
        if (totalQty == 0) {
            totalQty = totalQty + 1;
        }
        cartItem1.setQuantity(totalQty);
        cartItem1.setBook_format(format);
//        totalQty = cartItem.getQuantity() + quantity;
        if (format.equals(format)) {
//            cartItem1.setTotalPrice((float) (quantity * product.getPrinted_Price()));
//            //total
            totalPrice = product.getPrinted_Price() * totalQty;
            cartItem1.setTotalPrice((float) totalPrice);
        }
        if (format.equals("Audio")) {
//            cartItem1.setTotalPrice((float) (quantity * product.getAudio_price()));
//            //total
            totalPrice = product.getAudio_price() * totalQty;
            cartItem1.setTotalPrice((float) totalPrice);
        }
        if (format.equals("Pdf")) {
//            cartItem1.setTotalPrice((float) (quantity * product.getE_price()));
//            //total
            totalPrice = product.getE_price() * totalQty;
            cartItem1.setTotalPrice((float) totalPrice);
        }
//shopping cart
        cartItemRepository.save(cartItem1);
        subTotal = getSubTotal(shoppingCart);
        shoppingCart.setSubTotal(subTotal);
        if (subTotal > 150000) {
            shoppingCart.setShippingTotal(10000f);
            shoppingCart.setTotalAmount(subTotal + 10000f);
        } else {
            shoppingCart.setShippingTotal(20000f);
            shoppingCart.setTotalAmount(subTotal + 20000f);
        }
        shoppingCart.setDiscount_code("AN4234");
        shoppingCart.setDiscount_percent(5);
        shoppingCart.setDiscount_amount((double) (subTotal * 5 / 100));
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }

    public void emptyShoppingCart(Customer customer) {
        if (customer == null || customer.getShoppingCart() == null)
            return;

        shoppingCartRepository.delete(customer.getShoppingCart());
    }
}
