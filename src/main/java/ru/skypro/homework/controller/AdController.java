package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ads.Ad;
import ru.skypro.homework.dto.ads.Ads;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAd;
import ru.skypro.homework.service.AdService;

import javax.validation.Valid;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@Tag(name="Объявления")

public class AdController{

private final AdService adService;

// private final CommentService commentService;

@GetMapping
public ResponseEntity<Ads> getAllAds(){
    return ResponseEntity.ok(adService.getAllAds());
}

@PostMapping(consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<Ad> addAd(@RequestPart("properties") @Valid CreateOrUpdateAd properties,@RequestPart("image") MultipartFile image){
    return ResponseEntity.status(HttpStatus.CREATED).body(adService.addAd(properties,image));
}

@GetMapping("/{id}")
public ResponseEntity<ExtendedAd> getAd(@PathVariable Integer id){
    return ResponseEntity.ok(adService.getAd(id));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteAd(@PathVariable Integer id){
    adService.deleteAd(id);
    return ResponseEntity.noContent().build();
}

@PatchMapping("/{id}")
public ResponseEntity<Ad> updateAd(@PathVariable Integer id,@RequestBody @Valid CreateOrUpdateAd dto){
    return ResponseEntity.ok(adService.updateAd(id,dto));
}

@PatchMapping(value="/{id}/image", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<byte[]> updateImage(@PathVariable Integer id,@RequestPart("image") MultipartFile image){
    byte[] updatedImage=adService.updateImage(id,image);
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(updatedImage);
}

@GetMapping("/me")
public ResponseEntity<Ads> getMyAds(){
    return ResponseEntity.ok(adService.getMyAds());
}

/*

@GetMapping("/{id}/comments")
public ResponseEntity<Comments> getComments(@PathVariable Integer id){
    return ResponseEntity.ok(commentService.getCommentsByAdId(id));
}

@PostMapping("/{id}/comments")
public ResponseEntity<Comment> addComment(@PathVariable Integer id,@RequestBody @Valid CreateOrUpdateComment commentDto){
    return ResponseEntity.ok(commentService.addComment(id,commentDto));
}

@PatchMapping("/{adId}/comments/{commentId}")
public ResponseEntity<Comment> updateComment(@PathVariable Integer adId,@PathVariable Integer commentId,@RequestBody @Valid CreateOrUpdateComment commentDto){
    return ResponseEntity.ok(commentService.updateComment(adId,commentId,commentDto));
}

@DeleteMapping("/{adId}/comments/{commentId}")
public ResponseEntity<Void> deleteComment(@PathVariable Integer adId,@PathVariable Integer commentId){
    commentService.deleteComment(adId,commentId);
    return ResponseEntity.ok().build();
}

 */
}
