package online.market.service.common;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.City;
import online.market.model.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public void saveCity(City city) {
        cityRepository.save(city);
    }

    public City getCityById(Long id) {
        Optional<City> optionalCity = cityRepository.findById(id);
        return optionalCity.orElse(null);
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public void deleteCityById(Long id) {
        cityRepository.deleteById(id);
    }
}
