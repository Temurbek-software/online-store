package online.market.model.repository;

import online.market.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long>
{
    @Query(value = "SELECT ci.id, ci.created_at, ci.is_deleted," +
            " ci.updated_at, ci.format," +
            " ci.in_stock, ci.quantity, ci.total_price, ci.product_id, " +
            "ci.shopping_cart_id " +
            "FROM public.shopping_cart sc " +
            "JOIN public.cart_item ci ON sc.id = ci.shopping_cart_id " +
            "WHERE sc.customer_id = :customerId", nativeQuery = true)
    List<CartItem> findCartItemsByCustomerId(Long customerId);

    @Query(value = "SELECT count(*) " +
            "FROM public.shopping_cart sc " +
            "JOIN public.cart_item ci ON sc.id = ci.shopping_cart_id " +
            "WHERE sc.customer_id = :customerId", nativeQuery = true)
    int countCartItemsByCustomerId(Long customerId);
}
