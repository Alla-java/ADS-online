package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.*;
import ru.skypro.homework.service.AdService;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService{

@Override
public Ads getAllAds(){
    // TODO: Реализация будет позже
    throw new UnsupportedOperationException("Метод getAllAds() не реализован");
}


@Override
public Ad addAd(CreateOrUpdateAd dto,MultipartFile image){
    // TODO: Реализация будет позже
    throw new UnsupportedOperationException("Метод addAd() не реализован");

}

@Override
public ExtendedAd getAd(Integer id){
    // TODO: Реализация будет позже
    throw new UnsupportedOperationException("Метод getAd() не реализован");
}

@Override
public void updateImage(Integer id,MultipartFile image){
    // TODO: Реализация будет позже
    throw new UnsupportedOperationException("Метод updateImage не реализован");
}

}
