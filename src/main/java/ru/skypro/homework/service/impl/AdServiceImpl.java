package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.ads.*;

import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService{
private final AdRepository adRepository;
private final AdMapper adMapper;
private final UserService userService;
private final UserMapper userMapper;
private final UserRepository userRepository;
private final ImageService imageService;
private final ImageRepository imageRepository;

@Override
public AdDto addAd(CreateOrUpdateAd dto,MultipartFile image){

    User user=getCurrentUserEntity();
    Ad ad=adMapper.toEntity(dto,user);
    Ad savedAd=adRepository.save(ad);

    try{
        String imagePath=imageService.uploadAdImage(savedAd.getId(),image);

        String idStr=imagePath.substring(imagePath.lastIndexOf("/")+1);
        Integer imageId=Integer.parseInt(idStr);

        Image imageEntity=imageRepository.findById(imageId).orElseThrow(()->new RuntimeException("Не удалось найти изображение с id: "+imageId));

        savedAd.setImage(imageEntity);
        savedAd=adRepository.save(savedAd);

    }catch(IOException e){
        throw new RuntimeException("Ошибка загрузки изображения",e);
    }

    return adMapper.adIntoAdDto(savedAd);
}

@Override
public AdDto updateAd(Integer id,CreateOrUpdateAd dto){
    Ad ad=getAdOrThrow(id);
    User currentUser=getCurrentUserEntity();

    if(!isOwnerOrAdmin(ad,currentUser)){
        throw new AccessDeniedException("Вы не можете редактировать это объявление");
    }

    adMapper.updateEntity(ad,dto);
    return adMapper.adIntoAdDto(adRepository.save(ad));
}

@Override
public ExtendedAd getAd(Integer id){
    Ad ad=getAdOrThrow(id);
    return adMapper.toExtendedDto(ad);
}


@Override
public void deleteAd(Integer id){
    Ad ad=getAdOrThrow(id);
    User currentUser=getCurrentUserEntity();

    if(!isOwnerOrAdmin(ad,currentUser)){
        throw new AccessDeniedException("Вы не можете удалить это объявление");
    }

    adRepository.delete(ad);
}

@Override
public Ads getAllAds(){
    List<Ad> ads=adRepository.findAll();
    List<AdDto> adDtos=ads.stream().map(adMapper::adIntoAdDto).collect(Collectors.toList());
    return new Ads(adDtos.size(),adDtos);
}

@Override
public Ads getMyAds(){
    User user=getCurrentUserEntity();
    List<AdDto> ads=adRepository.findAllByAuthorId(user.getId()).stream().map(adMapper::adIntoAdDto).collect(Collectors.toList());
    return new Ads(ads.size(),ads);
}

private Ad getAdOrThrow(Integer id){
    return adRepository.findById(id).orElseThrow(()->new NoSuchElementException("Объявление не найдено"));
}

private boolean isOwnerOrAdmin(Ad ad,User currentUser){
    return ad.getAuthor().getId().equals(currentUser.getId())||currentUser.getRole()==Role.ADMIN;
}

private User getCurrentUserEntity(){
    String username=SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findUserByEmailIgnoreCase(username).orElseThrow(()->new UsernameNotFoundException("Пользователь не найден: "+username));
}

}



