package online.market.service.common;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Customer;
import online.market.model.entity.Order;
import online.market.model.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public List<Order> findByAllOrders(Customer customer) {
        return customer.getOrders();
    }

}
