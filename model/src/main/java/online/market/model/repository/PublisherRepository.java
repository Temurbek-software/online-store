package online.market.model.repository;

import online.market.model.entity.Company;
import online.market.model.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    @Query(value = "select * from publisher s where s.is_deleted=:deleted", nativeQuery = true)
    List<Publisher> getAllPublisherIfDeleted(boolean deleted);

    Publisher findPublisherById(long id);

    @Query(value = "SELECT * FROM publisher s WHERE extract (YEAR from s.created_at)=:year limit 3", nativeQuery = true)
    List<Publisher> getPublisherByCreatedAt(int year);
}
