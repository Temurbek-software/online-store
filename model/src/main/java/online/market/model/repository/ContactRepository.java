package online.market.model.repository;

import online.market.model.entity.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contacts,Long> {
}
