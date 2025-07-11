package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface ImageService {

    String uploadUserImage(MultipartFile image);

    String uploadAdImage(Integer adId, MultipartFile image);

    byte[] getImage(Integer id);


}
