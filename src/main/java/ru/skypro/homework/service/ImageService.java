package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface ImageService {

    String uploadUserImage(MultipartFile image) throws IOException;

    String uploadAdImage(Integer adId, MultipartFile image) throws IOException;

    byte[] getImage(Long id);


}
