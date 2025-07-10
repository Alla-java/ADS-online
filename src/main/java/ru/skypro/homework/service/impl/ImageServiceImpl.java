package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.service.ImageService;


@Service
public class ImageServiceImpl implements ImageService {
    private static final byte[] EMPTY_IMAGE = new byte[0];

    @Override
    public String uploadUserImage(MultipartFile image) {
        return "default_user_image.jpg";
    }

    @Override
    public String uploadAdImage(Integer adId, MultipartFile image) {
        return "default_ad_image_" + adId + ".jpg";
    }

    @Override
    public byte[] getImage(Integer id) {
        return EMPTY_IMAGE;
    }
}
