package online.market.service.entity.impl;

import lombok.RequiredArgsConstructor;
import online.market.model.entity.Advertisement;
import online.market.model.repository.AdvertisementRepository;
import online.market.service.entity.AdvertisementService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService
{
    private final AdvertisementRepository advertisementRepository;
    @Override
    public List<Advertisement> findAllItems() {
        return advertisementRepository.findAll();
    }

    @Override
    public void updateWithNewOne(Advertisement advertisement, Long id)
    {
        Optional<Advertisement> advertisement1=advertisementRepository.findById(id);
        if (advertisement1.isPresent())
        {
            Advertisement advertisement2 = advertisement1.get();
            advertisement2.setProduct(advertisement.getProduct());
            advertisement2.setDescription(advertisement.getDescription());
            advertisement2.setDate_released(advertisement.getDate_released());
            advertisement2.setTitleForAd(advertisement.getTitleForAd());
            advertisement2.setDiscount(advertisement.getDiscount());
            advertisement2.setImageData(advertisement.getImageData());
            advertisement2.setImage_posted1(advertisement.getImage_posted1());
            advertisementRepository.save(advertisement2);
        }
        else
        {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void deleteItems(Long id) {

    }

    @Override
    public boolean createNew(Advertisement advertisement)
    {
        advertisementRepository.save(advertisement);
        if (advertisement.getImage_posted1().getSize() > 0) {
            if (advertisement.getImage_posted1().getSize() > 0) {
                String image1 = ProductServiceImpl.ImageUpload(advertisement.getId(), advertisement.getImage_posted1(),"advertisement");
                advertisement.setImageData(image1);
            }
            advertisementRepository.save(advertisement);
        }
        return false;
    }
//    private String ImageUpload(Long productId, MultipartFile productImage1) {
//        String fileName = "";
//
//        String productFolder = "admin/src/main/resources/static/upload/product";
//
//        //Save image
//        try {
//            byte[] bytes = productImage1.getBytes();
//
//            //Create directory if not exists
//            File file = new File(productFolder + "/" + productId);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//
//            fileName = productImage1.getName() + ".png";
//
//            String fileWithFolderName = productFolder + "/" + productId + "/" + fileName;
//
//            BufferedOutputStream stream = new BufferedOutputStream(
//                    new FileOutputStream(
//                            new File(fileWithFolderName)));
//
//            stream.write(bytes);
//            stream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return fileName;
//    }
}
