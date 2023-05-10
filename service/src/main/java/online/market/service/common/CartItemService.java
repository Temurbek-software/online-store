package online.market.service.common;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.CartItem;
import online.market.model.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public List<CartItem> getCartItemsByCustomerId(Long customerId)
    {
        List<CartItem> cartItemsByCustomerId = cartItemRepository.findCartItemsByCustomerId(customerId);
        return cartItemsByCustomerId ==null?new ArrayList<>(): cartItemsByCustomerId;
    }
    public int counter(Long customerId)
    {
        return cartItemRepository.countCartItemsByCustomerId(customerId);
    }
}
