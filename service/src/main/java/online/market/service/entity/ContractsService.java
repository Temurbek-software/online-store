package online.market.service.entity;

import online.market.model.entity.Contracts;

import java.util.List;

public interface ContractsService {
    List<Contracts> contractList();
    void deleteContract(Long id);
    boolean createNewContract(Contracts contracts);
}
