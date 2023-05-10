package online.market.model.repository;

import online.market.model.entity.Contracts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ContractsRepository extends JpaRepository<Contracts,Long>
{
    @Transactional
    @Modifying
    @Query(value = "delete from contracts s where s.id=:id ", nativeQuery = true)
    void deleteContractsById(Long id);
}
