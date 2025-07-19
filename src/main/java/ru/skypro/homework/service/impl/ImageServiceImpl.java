package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    public String uploadUserImage(MultipartFile image) throws IOException {
        Image newImage = new Image();
        newImage.setData(image.getBytes());
        newImage.setMediaType(image.getContentType());
        Image savedImage = imageRepository.save(newImage);
        return "/images/" + savedImage.getId();
    }

    @Override
    public String uploadAdImage(Integer adId, MultipartFile image) throws IOException {
        Image newImage = new Image();
        newImage.setData(image.getBytes());
        newImage.setMediaType(image.getContentType());
        Image savedImage = imageRepository.save(newImage);
        return "/images/" + savedImage.getId();
    }

    @Override
    public byte[] getImage(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"))
                .getData();
    }
}
