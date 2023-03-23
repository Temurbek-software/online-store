package online.market.service.entity;


import online.market.model.entity.Publisher;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @apiNote  Extra and CRUD services for entity {@link Publisher}
 * @author Temurbek
 * @version 1.0
 */
public interface PublisherService {
    Publisher getOnePublisher(long id);
    List<Publisher> getAllPublisher(boolean info);
    void insertNewPublisher(Publisher publisherDTO);
    void updatePublisherByIdWithNewOne(long id,Publisher publisher);
    boolean deletePubById(long id);
    Page<Publisher> findPaginatedPublisher(int pageNo, int pageSize);
}
