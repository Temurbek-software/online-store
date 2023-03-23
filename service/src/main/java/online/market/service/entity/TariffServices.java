package online.market.service.entity;

import online.market.model.entity.Tariffs;

import java.util.List;

public interface TariffServices {
    List<Tariffs> getAllTariffs();
    void createNewTariff(Tariffs tariffs);
    Tariffs getOneTariff(Long id);
    void deleteTariffById(Long id);
}
