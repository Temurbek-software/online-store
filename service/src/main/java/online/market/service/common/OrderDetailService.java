package online.market.service.common;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.OrderDetail;
import online.market.model.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;


    public List<OrderDetail> findAllService() {
        return orderDetailRepository.findAll();
    }

    public void saveNewItem(OrderDetail order) {
        orderDetailRepository.save(order);
    }

    public OrderDetail getByOrderDetail(long id) {
        return orderDetailRepository.findById(id).get();
    }

    public void deleteByOrderDetailId(long id) {
        orderDetailRepository.deleteById(id);
    }
}
