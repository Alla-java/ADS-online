package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;

/**
 * Сервис для работы с изображениями
 */
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;

    @Value("${path.images}")
    private  String images;

    /**
     * Загрузка аватара пользователя
     * @param image файл изображения
     * @return путь к загруженному изображению
     * @throws IOException при ошибке работы с файлом
     */
    @Override
    public String uploadUserImage(MultipartFile image) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findUserByEmailIgnoreCase(username)
                     .orElseThrow(() -> new RuntimeException("User not found"));

        Image newImage = new Image();
        newImage.setData(image.getBytes());
        newImage.setMediaType(image.getContentType());

        Image savedImage = imageRepository.save(newImage);

        user.setImage(savedImage);
        userRepository.save(user);

        return images + savedImage.getId();
    }

    /**
     * Загрузка изображения объявления
     * @param adId идентификатор объявления
     * @param image файл изображения
     * @return путь к загруженному изображению
     * @throws IOException при ошибке работы с файлом
     */
    @Override
    public String uploadAdImage(Integer adId, MultipartFile image) throws IOException {

        Ad ad = adRepository.findById(adId)
                 .orElseThrow(() -> new RuntimeException("Ad not found"));

        Image newImage = new Image();
        newImage.setData(image.getBytes());
        newImage.setMediaType(image.getContentType());

        Image savedImage = imageRepository.save(newImage);

        ad.setImage(savedImage);
        adRepository.save(ad);

        return images + savedImage.getId();
    }

    /**
     * Получение изображения по идентификатору
     * @param id идентификатор изображения
     * @return массив байтов изображения
     */
    @Override
    public byte[] getImage(Integer id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"))
                .getData();
    }
}
