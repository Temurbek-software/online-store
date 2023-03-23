package online.market.service.common;

import lombok.RequiredArgsConstructor;
import online.market.model.repository.CartItemRepository;
import online.market.model.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

}
