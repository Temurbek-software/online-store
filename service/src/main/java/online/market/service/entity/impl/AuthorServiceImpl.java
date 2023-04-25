package online.market.service.entity.impl;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Author;
import online.market.model.entity.Product;
import online.market.model.repository.AuthorRepository;
import online.market.model.repository.ProductRepository;
import online.market.service.entity.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final ProductRepository productRepository;


    @Override
    public Page<Author> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.authorRepository.findAll(pageable);
    }

    @Override
    public List<Author> listOfAuthorsCreatedAt(int year) {
        List<Author> authorList=authorRepository.getAuthorByCreatedAt(year);
        return authorList;
    }

    @Override
    public List<Author> authorDtoList(boolean data) {
        List<Author> allByAuthorByDeletedOrNotDeleted = authorRepository.getAllByAuthorByDeletedOrNotDeleted(data);
        return allByAuthorByDeletedOrNotDeleted;
    }

    @Override
    public Author getOneAuthorDto(long id) {
        return authorRepository.findById(id).get();
    }

    @Override
    public void saveAuthor(Author authorDTO) throws IOException {
        updateProduct(authorDTO);
        authorRepository.save(authorDTO);
        if (authorDTO.getImage_posted1().getSize() > 0) {
            String image1 = ImageUpload(authorDTO.getId(), authorDTO.getImage_posted1());
            authorDTO.setImage1(image1);
            authorRepository.save(authorDTO);
        }

    }

    private String ImageUpload(Long productId, MultipartFile productImage1) {
        String fileName = "";

        String productFolder = "admin/src/main/resources/static/upload/author";

        //Save image
        try {
            byte[] bytes = productImage1.getBytes();

            //Create directory if not exists
            File file = new File(productFolder + "/" + productId);
            if (!file.exists()) {
                file.mkdirs();
            }

            fileName = productImage1.getName() + ".png";

            String fileWithFolderName = productFolder + "/" + productId + "/" + fileName;

            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(
                            new File(fileWithFolderName)));

            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }


    @Override
    public boolean deleteAuthorById(long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            optionalAuthor.get().removeAllProducts();
            authorRepository.deleteById(optionalAuthor.get().getId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateAuthorById(long id, Author authorDTO) {
        Author author = authorRepository.findById(id).get();
        author.removeAllProducts();
        authorRepository.save(author);
        if (authorDTO.getImage_posted1().getSize() != 0) {
            author.setImage_posted1(authorDTO.getImage_posted1());
            String image1 = ImageUpload(authorDTO.getId(), authorDTO.getImage_posted1());
            authorDTO.setImage1(image1);
            authorRepository.save(authorDTO);
        }
        updateProduct(authorDTO);
        authorRepository.save(authorDTO);
    }

    public void updateProduct(Author author1) {
        if (!author1.getProductDTOSet().isEmpty()) {
            if (author1.getProductDTOSet() == null) {
                author1.setProductDTOSet(new HashSet<>());
            }
            author1.getProductDTOSet().stream().forEach(product ->
            {
                Product product1 = productRepository.findProductById(product.getId());
                if (product1 == null) {
                    product1 = new Product();
                    product.setProductAuthorList(new HashSet<>());
                }
                author1.addProduct(product1);
            });
        }
    }

}
