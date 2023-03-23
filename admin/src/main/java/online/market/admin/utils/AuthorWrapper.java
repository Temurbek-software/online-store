package online.market.admin.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorWrapper {
    private Long id;
    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;
    private String shortDescription;
    private String description;

    private String image1;
    private MultipartFile image_posted1;

    private String[] productDTOSet;

    public String getFullImage1Url() {
        if (id != null && image1 != null) {
            return "/upload/author/" + id + "/" + image1;
        } else {
            return "/upload/no_preview.jpg";
        }
    }


}
