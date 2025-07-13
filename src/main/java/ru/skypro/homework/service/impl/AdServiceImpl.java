package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.*;
import ru.skypro.homework.service.AdService;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService{

private final Map<Integer,Ad> ads=new HashMap<>();
private final AtomicInteger idGenerator=new AtomicInteger(1);

private static final int CURRENT_USER_ID=1;

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
public void deleteAd(Integer id){
    // TODO: Реализация будет позже
    throw new UnsupportedOperationException("Метод deleteAd() не реализован");
}


@Override
public Ad updateAd(Integer id,CreateOrUpdateAd dto){
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

