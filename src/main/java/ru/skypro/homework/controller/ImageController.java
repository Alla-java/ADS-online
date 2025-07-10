package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.service.ImageService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Изображения")
@CrossOrigin(value = "http://localhost:3000")
public class ImageController {
    private final ImageService imageService;

    @Operation(summary = "Обновление аватара пользователя")
    @PatchMapping(value = "/users/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestParam MultipartFile image) {
        imageService.uploadUserImage(image);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Получение аватара пользователя")
    @GetMapping(value = "/users/{userId}/image", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getUserImage(@PathVariable Integer userId) {
        return ResponseEntity.ok(imageService.getImage(userId));
    }

    @Operation(summary = "Обновление изображения объявления")
    @PatchMapping(value = "/ads/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateAdImage(@PathVariable Integer id,
                                              @RequestParam MultipartFile image) {
        imageService.uploadAdImage(id, image);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Получение изображения объявления")
    @GetMapping(value = "/ads/{id}/image", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getAdImage(@PathVariable Integer id) {
        return ResponseEntity.ok(imageService.getImage(id));
    }
}
