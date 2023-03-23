package online.market.client.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import online.market.model.entity.Product;

import java.util.Date;

@Data
@AllArgsConstructor
public class ProductWrapper {
    private String bookName;
    private Double e_price;
    private Double printed_Price;
    private Double audio_price;
    private Date yearOfPublished;
    private Integer pageNumb;
    private String description;
    private String language;
    private String isbnNumber;
    private String imageData;
    private String categoryItems;
    private String subCategory;
    private String productCompany;
    private String productPublisherList;
    private String[] productAuthorList;
    public String fullImageUrl;

    public ProductWrapper(Product product)
    {
        this.bookName=product.getBookName();
        this.e_price=product.getE_price();
        this.printed_Price=product.getPrinted_Price();
        this.audio_price=product.getAudio_price();
        this.yearOfPublished=product.getYearOfPublished();
        this.pageNumb=product.getPageNumb();
        this.description=product.getDescription();
        this.language=product.getLanguage();
        this.isbnNumber=product.getIsbnNumber();
        this.imageData=product.getImageData();
        this.categoryItems=product.getCategoryItems().getName();
        this.productCompany=product.getProductCompany().getNameOfCompany();
        this.productPublisherList=product.getProductPublisherList().getName();

    }
}
