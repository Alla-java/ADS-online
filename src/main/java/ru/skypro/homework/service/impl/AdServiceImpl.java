package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.*;
import ru.skypro.homework.service.AdService;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;



//Все методы заглушены постоянными значениями (реализация сервиса на 2-ом этапе)

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService{

private final Map<Integer,Ad> ads=new HashMap<>();
private final AtomicInteger idGenerator=new AtomicInteger(1);

private static final int CURRENT_USER_ID=1;

@Override
public Ads getAllAds(){
    List<Ad> adList=new ArrayList<>(ads.values());
    return new Ads(adList.size(),adList);
}

@Override
public Ad addAd(CreateOrUpdateAd dto,MultipartFile image){
    int id=idGenerator.getAndIncrement();

    String imageUrl="mock-image-url-"+id;

    Ad ad=new Ad(CURRENT_USER_ID,imageUrl,id,dto.getPrice(),dto.getTitle());
    ads.put(id,ad);
    return ad;
}

@Override
public ExtendedAd getAd(Integer id){
    Ad ad=ads.get(id);
    if(ad==null){
        throw new RuntimeException("Объявление не найдено");
    }

    return new ExtendedAd(ad.getPk(),"Иван","Иванов","Описание мок объявления","ivanov@example.com",ad.getImage(),"+7 999 999 99 99",ad.getPrice(),ad.getTitle());
}

@Override
public void deleteAd(Integer id){
    ads.remove(id);
}

@Override
public Ad updateAd(Integer id,CreateOrUpdateAd dto){
    Ad existing=ads.get(id);
    if(existing==null){
        throw new RuntimeException("Объявление не найдено");
    }
    Ad updated=new Ad(existing.getAuthor(),existing.getImage(),id,dto.getPrice(),dto.getTitle());
    ads.put(id,updated);
    return updated;
}

@Override
public byte[] updateImage(Integer id,MultipartFile image){
    Ad existing=ads.get(id);
    if(existing==null){
        throw new RuntimeException("Объявление не найдено");
    }

    String newImageUrl="mock-updated-image-url-"+id;
    Ad updated=new Ad(existing.getAuthor(),newImageUrl,id,existing.getPrice(),existing.getTitle());
    ads.put(id,updated);

    return newImageUrl.getBytes(StandardCharsets.UTF_8);
}

@Override
public Ads getMyAds(){
    List<Ad> myAds=ads.values().stream().filter(ad->ad.getAuthor().equals(CURRENT_USER_ID)).collect(Collectors.toList());
    return new Ads(myAds.size(),myAds);
}
}
