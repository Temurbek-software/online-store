package online.market.service.entity.impl;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Tariffs;
import online.market.model.repository.TariffRepository;
import online.market.service.entity.TariffServices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TariffServicesImpl implements TariffServices {
    private final TariffRepository tariffRepository;

    @Override
    public List<Tariffs> getAllTariffs() {
        return tariffRepository.findAll();
    }

    @Override
    public void createNewTariff(Tariffs tariffs) {
      tariffRepository.save(tariffs);
    }

    @Override
    public Tariffs getOneTariff(Long id) {
        return tariffRepository.getOne(id);
    }

    @Override
    public void deleteTariffById(Long id) {
       tariffRepository.deleteById(id);
    }
}
