package online.market.service.entity;


import online.market.model.entity.Author;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

/**
 * @apiNote  Extra and CRUD services for entity Author
 * @author Temurbek
 * @version 1.0
 */
public interface AuthorService {
    List<Author> authorDtoList(boolean info);
    Author getOneAuthorDto(long id);
    void saveAuthor(Author authorDTO) throws IOException;
    boolean deleteAuthorById(long id);
    void updateAuthorById(long id,Author authorDTO);
    Page<Author> findPaginated(int pageNo,int pageSize);
    List<Author> listOfAuthorsCreatedAt(int year);
}
