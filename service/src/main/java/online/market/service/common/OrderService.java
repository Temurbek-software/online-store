package online.market.service.common;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Customer;
import online.market.model.entity.Order;
import online.market.model.entity.Tariffs;
import online.market.model.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderService
{
    private final OrderRepository orderRepository;
    public void saveOrderTariff(Tariffs tariffs, Customer customer) {
        Order order = new Order();
        Order existingOrder = orderRepository.findOrderByCustomerId(customer.getId());
        if (existingOrder == null) {
            order.setDescription("New tariff added to new order");
            order.setDeliveryDate(null);
            order.setOrderDetailList(new ArrayList<>());
            order.setSubTotal((float) (tariffs.getPriceFor() * (1-(tariffs.getDiscount() / 100))));
            order.setCustomer(customer);
        } else {
            order = existingOrder;
            order.setDescription("New tariff added to the order");
            float numbers=(float) (tariffs.getPriceFor() * (1-(tariffs.getDiscount() / 100)));
            order.setSubTotal(order.getSubTotal()==null?numbers:order.getSubTotal()+numbers);
        }
        order.setOrderDate(new Date());
        order.setOrderTariff(tariffs);
        order.setOrderStatus("In progress");
        orderRepository.save(order);
    }

}
