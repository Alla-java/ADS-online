package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.*;

import ru.skypro.homework.service.AdService;

@Service
public class AdServiceImpl implements AdService{

@Override
public Ads getAllAds(){
    // TODO: Реализация будет позже
    throw new UnsupportedOperationException("Метод addAd() не реализован");
}

@Override
public AdDto addAd(CreateOrUpdateAd dto,MultipartFile image){
    // TODO: Реализация будет позже
    throw new UnsupportedOperationException("Метод addAd() не реализован");
}

@Override
public ExtendedAdDto getAd(Integer id){
    // TODO: Реализация будет позже
    throw new UnsupportedOperationException("Метод getAd() не реализован");
}

@Override
public void deleteAd(Integer id){
    // TODO: Реализация будет позже
    throw new UnsupportedOperationException("Метод deleteAd() не реализован");
}

@Override
public AdDto updateAd(Integer id,CreateOrUpdateAd dto){
    // TODO: Реализация будет позже
    throw new UnsupportedOperationException("Метод updateAd() не реализован");
}

@Override
public byte[] updateImage(Integer id,MultipartFile image){
    // TODO: Реализация будет позже
    throw new UnsupportedOperationException("Метод updateImage() не реализован");
}

@Override
public Ads getMyAds(){
    // TODO: Реализация будет позже
    throw new UnsupportedOperationException("Метод getMyAds() не реализован");
}
}

