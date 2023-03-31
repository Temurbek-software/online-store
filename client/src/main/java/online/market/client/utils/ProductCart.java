package online.market.client.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class ProductCart {
    private Long product_id;
    private Long quantity;
    private String format_book;
}
