package ru.skypro.homework.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.Ads;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAd;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.impl.AdServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdServiceImplTests{

@Mock private AdRepository adRepository;
@Mock private AdMapper adMapper;
@Mock private UserRepository userRepository;
@Mock private ImageService imageService;
@Mock private ImageRepository imageRepository;

@InjectMocks
private AdServiceImpl adService;

private User testUser;
private Ad testAd;
private CreateOrUpdateAd createDto;

@BeforeEach
void setup() {
    testUser = new User();
    testUser.setId(1);
    testUser.setEmail("user@example.com");

    testAd = new Ad();
    testAd.setId(1);
    testAd.setAuthor(testUser);

    createDto = new CreateOrUpdateAd();
    createDto.setTitle("Test Ad");
    createDto.setDescription("Description");
}

private void mockAuthentication() {
    Authentication auth = mock(Authentication.class);
    when(auth.getName()).thenReturn(testUser.getEmail());
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(auth);
    SecurityContextHolder.setContext(securityContext);
}

@Test
void addAd_shouldSaveAndReturnAdDto() throws Exception {
    mockAuthentication();

    MultipartFile file = mock(MultipartFile.class);

    when(userRepository.findUserByEmailIgnoreCase(testUser.getEmail())).thenReturn(Optional.of(testUser));
    when(adMapper.toEntity(createDto, testUser)).thenReturn(testAd);
    when(adRepository.save(testAd)).thenReturn(testAd);
    when(imageService.uploadAdImage(testAd.getId(), file)).thenReturn("/images/10");
    Image image = new Image();
    image.setId(10);
    when(imageRepository.findById(10)).thenReturn(Optional.of(image));
    when(adRepository.save(testAd)).thenReturn(testAd);
    AdDto adDto = new AdDto();
    adDto.setPk(testAd.getId());
    when(adMapper.adIntoAdDto(testAd)).thenReturn(adDto);

    AdDto result = adService.addAd(createDto, file);

    assertNotNull(result);
    assertEquals(testAd.getId(), result.getPk());
    verify(adRepository, times(2)).save(testAd);
    verify(imageService).uploadAdImage(testAd.getId(), file);
}

@Test
void updateAd_shouldUpdateIfOwnerOrAdmin() {
    mockAuthentication();

    CreateOrUpdateAd updateDto = new CreateOrUpdateAd();
    updateDto.setTitle("Updated Title");

    testAd.setAuthor(testUser);
    when(adRepository.findById(1)).thenReturn(Optional.of(testAd));
    when(userRepository.findUserByEmailIgnoreCase(testUser.getEmail())).thenReturn(Optional.of(testUser));

    when(adRepository.save(any())).thenReturn(testAd);
    when(adMapper.adIntoAdDto(testAd)).thenReturn(new AdDto());

    AdDto updated = adService.updateAd(1, updateDto);

    assertNotNull(updated);
    verify(adRepository).save(testAd);
}

@Test
void updateAd_shouldThrowAccessDeniedIfNotOwner() {
    mockAuthentication();

    User anotherUser = new User();
    anotherUser.setId(2);
    testAd.setAuthor(anotherUser);

    when(adRepository.findById(1)).thenReturn(Optional.of(testAd));
    when(userRepository.findUserByEmailIgnoreCase(testUser.getEmail())).thenReturn(Optional.of(testUser));

    assertThrows(AccessDeniedException.class, () -> adService.updateAd(1, createDto));
}

@Test
void deleteAd_shouldDeleteIfOwnerOrAdmin() {
    mockAuthentication();

    testAd.setAuthor(testUser);
    when(adRepository.findById(1)).thenReturn(Optional.of(testAd));
    when(userRepository.findUserByEmailIgnoreCase(testUser.getEmail())).thenReturn(Optional.of(testUser));

    adService.deleteAd(1);

    verify(adRepository).delete(testAd);
}

@Test
void deleteAd_shouldThrowAccessDeniedIfNotOwner() {
    mockAuthentication();

    User anotherUser = new User();
    anotherUser.setId(2);
    testAd.setAuthor(anotherUser);

    when(adRepository.findById(1)).thenReturn(Optional.of(testAd));
    when(userRepository.findUserByEmailIgnoreCase(testUser.getEmail())).thenReturn(Optional.of(testUser));

    assertThrows(AccessDeniedException.class, () -> adService.deleteAd(1));
}

@Test
void getAd_shouldReturnExtendedAdDto() {
    when(adRepository.findById(1)).thenReturn(Optional.of(testAd));
    ExtendedAd extendedAd = new ExtendedAd();
    when(adMapper.toExtendedDto(testAd)).thenReturn(extendedAd);

    ExtendedAd result = adService.getAd(1);

    assertEquals(extendedAd, result);
}

@Test
void getAllAds_shouldReturnAds() {
    List<Ad> ads = List.of(testAd);
    when(adRepository.findAll()).thenReturn(ads);
    AdDto adDto = new AdDto();
    when(adMapper.adIntoAdDto(testAd)).thenReturn(adDto);

    Ads result = adService.getAllAds();

    assertEquals(1, result.getCount());
    assertEquals(adDto, result.getResults().get(0));
}

@Test
void getMyAds_shouldReturnUserAds() {
    mockAuthentication();

    when(userRepository.findUserByEmailIgnoreCase(testUser.getEmail())).thenReturn(Optional.of(testUser));
    List<Ad> userAds = List.of(testAd);
    when(adRepository.findAllByAuthorId(testUser.getId())).thenReturn(userAds);
    AdDto adDto = new AdDto();
    when(adMapper.adIntoAdDto(testAd)).thenReturn(adDto);

    Ads result = adService.getMyAds();

    assertEquals(1, result.getCount());
    assertEquals(adDto, result.getResults().get(0));
}
}