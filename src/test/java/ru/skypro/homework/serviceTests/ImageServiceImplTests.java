package ru.skypro.homework.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.ImageServiceImpl;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageServiceImplTests{

@Mock
private ImageRepository imageRepository;

@Mock
private AdRepository adRepository;

@Mock
private UserRepository userRepository;

@InjectMocks
private ImageServiceImpl imageService;

@Mock
private SecurityContext securityContext;

@Mock
private Authentication authentication;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);

    // Подменяем SecurityContextHolder
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
}

@Test
void uploadUserImage_shouldSaveImageAndReturnPath() throws IOException {
    // Arrange
    byte[] imageBytes = "image-data".getBytes();
    MockMultipartFile multipartFile = new MockMultipartFile("image", "filename.jpg", "image/jpeg", imageBytes);
    User user = new User();
    user.setEmail("test@example.com");

    when(authentication.getName()).thenReturn("test@example.com");
    when(userRepository.findUserByEmailIgnoreCase("test@example.com")).thenReturn(Optional.of(user));

    Image savedImage = new Image();
    savedImage.setId(1);
    when(imageRepository.save(any(Image.class))).thenReturn(savedImage);
    when(userRepository.save(user)).thenReturn(user);

    // Act
    String path = imageService.uploadUserImage(multipartFile);

    // Assert
    assertEquals("/images/1", path);
    verify(imageRepository).save(any(Image.class));
    verify(userRepository).save(user);
    assertEquals(savedImage, user.getImage());
}

@Test
void uploadUserImage_whenUserNotFound_shouldThrow() {
    when(authentication.getName()).thenReturn("test@example.com");
    when(userRepository.findUserByEmailIgnoreCase("test@example.com")).thenReturn(Optional.empty());

    MockMultipartFile multipartFile = new MockMultipartFile("image", "filename.jpg", "image/jpeg", new byte[0]);

    RuntimeException ex = assertThrows(RuntimeException.class, () -> imageService.uploadUserImage(multipartFile));
    assertEquals("User not found", ex.getMessage());
}

@Test
void uploadAdImage_shouldSaveImageAndReturnPath() throws IOException {
    byte[] imageBytes = "ad-image".getBytes();
    MockMultipartFile multipartFile = new MockMultipartFile("image", "ad.jpg", "image/jpeg", imageBytes);
    Ad ad = new Ad();

    when(adRepository.findById(1)).thenReturn(Optional.of(ad));
    Image savedImage = new Image();
    savedImage.setId(2);
    when(imageRepository.save(any(Image.class))).thenReturn(savedImage);
    when(adRepository.save(ad)).thenReturn(ad);

    String path = imageService.uploadAdImage(1, multipartFile);

    assertEquals("/images/2", path);
    verify(imageRepository).save(any(Image.class));
    verify(adRepository).save(ad);
    assertEquals(savedImage, ad.getImage());
}

@Test
void uploadAdImage_whenAdNotFound_shouldThrow() {
    when(adRepository.findById(1)).thenReturn(Optional.empty());
    MockMultipartFile multipartFile = new MockMultipartFile("image", "ad.jpg", "image/jpeg", new byte[0]);

    RuntimeException ex = assertThrows(RuntimeException.class, () -> imageService.uploadAdImage(1, multipartFile));
    assertEquals("Ad not found", ex.getMessage());
}

@Test
void getImage_shouldReturnImageData() {
    byte[] data = "some-image-data".getBytes();
    Image image = new Image();
    image.setData(data);

    when(imageRepository.findById(1)).thenReturn(Optional.of(image));

    byte[] result = imageService.getImage(1);
    assertArrayEquals(data, result);
}

@Test
void getImage_whenNotFound_shouldThrow() {
    when(imageRepository.findById(1)).thenReturn(Optional.empty());

    RuntimeException ex = assertThrows(RuntimeException.class, () -> imageService.getImage(1));
    assertEquals("Image not found", ex.getMessage());
}
}
