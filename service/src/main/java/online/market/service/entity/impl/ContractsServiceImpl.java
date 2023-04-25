package online.market.service.entity.impl;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Contracts;
import online.market.model.repository.ContractsRepository;
import online.market.service.entity.ContractsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractsServiceImpl implements ContractsService
{
  private final ContractsRepository contractsRepository;

    @Override
    public List<Contracts> contractList()
    {
        return contractsRepository.findAll();
    }

    @Override
    public void deleteContract(Long id) {
        Optional<Contracts> optionalContracts=contractsRepository.findById(id);
        if (optionalContracts.isPresent())
        {
            contractsRepository.deleteContractsById(id);
        }
    }

    @Override
    public boolean createNewContract(Contracts contracts) {
        contractsRepository.save(contracts);
        if (contracts.getImage_posted1().getSize() > 0) {
            if (contracts.getImage_posted1().getSize() > 0) {
                String image1 = ProductServiceImpl.ImageUpload(contracts.getId(), contracts.getImage_posted1(), "contracts","image");
                contracts.setImageData(image1);
            }
            contractsRepository.save(contracts);
        }
        return false;
    }


}
