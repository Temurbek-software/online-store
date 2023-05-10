package online.market.model.repository;

import online.market.model.entity.CustomerTariff;
import online.market.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerTariffRepository extends JpaRepository<CustomerTariff, Long>
{
    @Query(value = " SELECT s.id, s.end_date, s.start_date, s.customer_id, s.tariff_id FROM public.customer_tariffs s where s.customer_id= :id",nativeQuery = true)
    List<CustomerTariff> findByCustomerAndTariffs(Long id);

}

