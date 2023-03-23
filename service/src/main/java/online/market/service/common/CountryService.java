package online.market.service.common;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Country;
import online.market.model.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {
  private final CountryRepository countryRepository;

    public void saveCountry(Country country) {
        countryRepository.save(country);
    }

    public Country getCountryById(Long id) {
        Optional<Country> optionalCountry = countryRepository.findById(id);
        return optionalCountry.orElse(null);
    }

    public List<Country> getAllCountries()
    {
        return countryRepository.findAll();
    }

    public void deleteCountryById(Long id) {
        countryRepository.deleteById(id);
    }

    public Country getCountryByName(String name) {
        return countryRepository.findByCountryName(name);
    }
}
