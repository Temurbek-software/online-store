package online.market.model.repository;

import online.market.model.entity.Tariffs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TariffRepository extends JpaRepository<Tariffs,Long>
{
    @Query(value = "select * from tariffs s",nativeQuery = true)
    List<Tariffs> getTariffsBy();
}
