package online.market.service.entity;

import online.market.model.entity.Advertisement;

import java.util.List;

public interface AdvertisementService {
    List<Advertisement> findAllItems();
    void updateWithNewOne(Advertisement advertisement,Long id);
    void deleteItems(Long id);
    boolean createNew(Advertisement advertisement);
}
