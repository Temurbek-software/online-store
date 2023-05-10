package online.market.model.repository;

import online.market.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface OrderRepository extends JpaRepository<Order, Long>
{
    @Query(value = "SELECT * FROM public.orders WHERE customer_id = :customerId",nativeQuery = true)
    Order findOrderByCustomerId(@Param("customerId") Long customerId);
}
