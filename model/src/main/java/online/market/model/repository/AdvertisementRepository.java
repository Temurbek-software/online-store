package online.market.model.repository;

import online.market.model.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long>
{
    @Transactional
    @Modifying
    @Query(value = "delete from advertisement s where s.id=:id ", nativeQuery = true)
    void deleteAdvertisementById(Long id);
}
