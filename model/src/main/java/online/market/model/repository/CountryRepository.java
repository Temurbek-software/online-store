package online.market.model.repository;

import online.market.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country,Long>
{
    Country findByCountryName(String name);
}
