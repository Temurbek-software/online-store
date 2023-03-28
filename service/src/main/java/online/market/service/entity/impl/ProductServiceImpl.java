package online.market.service.entity.impl;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Author;
import online.market.model.entity.Product;
import online.market.model.entity.SubCategory;
import online.market.model.repository.*;
import online.market.service.entity.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/***
 * @see ProductService
 * @author Temurbek
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final PublisherRepository publisherRepository;
    private final CompanyRepository companyRepository;
    private final AuthorRepository authorRepository;

    @Override
    public List<Product> productDtoList(boolean data) {
        List<Product> productList = productRepository.getAllByProductsIfNotDeleted(data);
        return productList;
    }

    @Override
    public Product getOneProductDto(long id) {

        return productRepository.findById(id).get();
    }

    @Override
    public void saveNewProduct(Product productDTO) throws IOException {
        commonUtils(productDTO);
        productRepository.save(productDTO);
        if (productDTO.getImage_posted1().getSize() > 0) {
            if (productDTO.getImage_posted1().getSize() > 0) {
                String image1 = ImageUpload(productDTO.getId(), productDTO.getImage_posted1(),"product");
                productDTO.setImageData(image1);
            }
            productRepository.save(productDTO);
        }
    }

    public static String ImageUpload(Long productId, MultipartFile productImage1,String folderName) {
        String fileName = "";

        String productFolder = "admin/src/main/resources/static/upload/"+folderName;

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
    public List<Product> getProductsBySubcategoryId(Long id) {
        List<Product> productList=productRepository.findProductByCategoryItems(id);
        return productList;
    }

    @Override
    public List<Product> productListBySubCategoryId(Long id, Object o) {
        if (o.getClass() == SubCategory.class) {
            List<Product> productList = productRepository.findAll()
                    .stream().filter(product -> product.getSubCategory().id == id)
                    .collect(Collectors.toList());
            return productList;
        } else {
            List<Product> productList2 = productRepository.findAll()
                    .stream().filter(product -> product.getCategoryItems().id == id)
                    .collect(Collectors.toList());
            return productList2;
        }
    }

    @Override
    public void updateProduct(long id, Product productDTO) {
        Product product = productRepository.findById(id).get();
        product.removeAuthors();
        productRepository.save(product);

        if (productDTO.getImage_posted1().getSize() != 0) {
            product.setImage_posted1(productDTO.getImage_posted1());
            String image1 = ImageUpload(productDTO.getId(), productDTO.getImage_posted1(),"product");
            productDTO.setImageData(image1);
            productRepository.save(productDTO);
        }
        commonUtils(productDTO);
        productRepository.save(productDTO);
    }

    public void commonUtils(Product product2) {
        long categoryId = product2.getCategoryItems().getId();
        product2.setCategoryItems(categoryRepository.findCategoryById(categoryId));

        long subcategoryId = product2.getSubCategory().getId();
        product2.setSubCategory(subCategoryRepository.findSubCategoryById(subcategoryId));

        long publisherId = product2.getProductPublisherList().getId();
        product2.setProductPublisherList(publisherRepository.findPublisherById(publisherId));

        long companyId = product2.getProductCompany().getId();
        product2.setProductCompany(companyRepository.findCompanyById(companyId));

        if (!product2.getProductAuthorList().isEmpty()) {
            if (product2.getProductAuthorList() == null) {
                product2.setProductAuthorList(new HashSet<>());
            }
            product2.getProductAuthorList().stream().forEach(authorDTO ->
            {
//                System.out.println(authorDTO.getFirstName());
                Author author = authorRepository.getAuthorById(authorDTO.getId());
                if (author == null) {
                    author = new Author();
                    author.setProductDTOSet(new HashSet<>());
                }
                product2.addAuthor(author);
            });
        }
    }

    @Override
    public boolean deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            optionalProduct.get().removeAuthors();
            productRepository.deleteById(optionalProduct.get().getId());
            return true;
        } else {
            return false;
        }
    }

}
