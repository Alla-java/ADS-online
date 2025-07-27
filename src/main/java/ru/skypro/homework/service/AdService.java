package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.Ads;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAdDto;

public interface AdService{

Ads getAllAds();

AdDto addAd(CreateOrUpdateAd dto,MultipartFile image) throws Exception;

ExtendedAdDto getAd(Integer id);

void deleteAd(Integer id);

AdDto updateAd(Integer id,CreateOrUpdateAd dto);

Ads getMyAds();
}
