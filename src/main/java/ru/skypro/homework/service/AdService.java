package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.Ad;
import ru.skypro.homework.dto.ads.Ads;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAd;

public interface AdService{

Ads getAllAds();

Ad addAd(CreateOrUpdateAd dto,MultipartFile image);

ExtendedAd getAd(Integer id);

void deleteAd(Integer id);

Ad updateAd(Integer id,CreateOrUpdateAd dto);

byte[] updateImage(Integer id,MultipartFile image);

Ads getMyAds();
}
