package online.market.model.repository;

import online.market.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Long>
{
    Users findByUsername(String name);
    Users findByFirstName(String name);
}
