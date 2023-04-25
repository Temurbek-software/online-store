package online.market.model.repository;

import online.market.model.entity.Author;
import online.market.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>
{
    @Query(value = "select * from author s where s.is_deleted=:deleted limit 4", nativeQuery = true)
    List<Author> getAllByAuthorByDeletedOrNotDeleted(boolean deleted);

    Author getAuthorById(long id);

    // get mostly active authors
    @Query(value = "select * from author limit 4", nativeQuery = true)
    List<Author> getAuthorsByActiveMost();

    @Query(value = "SELECT * FROM author s WHERE extract (YEAR from s.created_at)=:year limit 3", nativeQuery = true)
    List<Author> getAuthorByCreatedAt(int year);


//    @Modifying
//    @Query("update User u set u.firstname = ?1, u.lastname = ?2 where u.id = ?3")
//    void setAuthorById(String firstname, String lastname, Integer userId);

//    Author findAuthorById(long id);

}
