package online.market.model.repository;

import online.market.model.entity.Tariffs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffRepository extends JpaRepository<Tariffs,Long>
{

}
