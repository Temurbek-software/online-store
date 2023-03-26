package online.market.model.repository;

import online.market.model.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement,Long>
{

}
