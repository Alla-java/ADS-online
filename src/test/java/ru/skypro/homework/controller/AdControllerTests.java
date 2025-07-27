package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.dto.ads.AdDto;
import ru.skypro.homework.dto.ads.Ads;
import ru.skypro.homework.dto.ads.CreateOrUpdateAd;
import ru.skypro.homework.dto.ads.ExtendedAdDto;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.SecurityService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdController.class)
public class AdControllerTests {

@Autowired
private MockMvc mockMvc;

@MockBean
private AdService adService;

@MockBean
private SecurityService securityService;

@Autowired
private ObjectMapper objectMapper;

@Test
@WithMockUser(roles = "USER")
void getAllAds_ReturnsAds() throws Exception {

    AdDto adDto = AdDto.builder()
                   .pk(1)
                   .title("Ad Title")
                   .price(100)
                   .author(1)
                   .image("imageUrl")
                   .build();

    Ads ads = Ads.builder()
               .count(1)
               .results(List.of(adDto))
               .build();

    Mockito.when(adService.getAllAds()).thenReturn(ads);

    mockMvc.perform(get("/ads"))
     .andExpect(status().isOk())
     .andExpect(jsonPath("$.count").value(1))
     .andExpect(jsonPath("$.results[0].pk").value(1))
     .andExpect(jsonPath("$.results[0].title").value("Ad Title"))
     .andExpect(jsonPath("$.results[0].price").value(100))
     .andExpect(jsonPath("$.results[0].author").value(1))
     .andExpect(jsonPath("$.results[0].image").value("imageUrl"));
}


@Test
@WithMockUser(roles = "USER")
void addAd_ReturnsCreatedAd() throws Exception {
    MockMultipartFile image = new MockMultipartFile(
     "image", "test.png",
     MediaType.IMAGE_PNG_VALUE, "image".getBytes()
    );

    AdDto createdAd = AdDto.builder()
                       .pk(1)
                       .title("title")
                       .price(100)
                       .author(1)
                       .image("imageUrl")
                       .build();

    Mockito.when(adService.addAd(any(CreateOrUpdateAd.class), any()))
     .thenReturn(createdAd);

    mockMvc.perform(multipart("/ads")
                     .file(image)
                     .with(csrf())
                     .param("title", "title")
                     .param("price", "100")
                     .param("description", "description"))
     .andExpect(status().isOk())
     .andExpect(jsonPath("$.pk").value(1))
     .andExpect(jsonPath("$.title").value("title"))
     .andExpect(jsonPath("$.price").value(100));
}

@Test
@WithMockUser(roles = "USER")
void getAd_ReturnsExtendedAdDto() throws Exception {
    ExtendedAdDto extendedAdDto = ExtendedAdDto.builder()
                                   .pk(1)
                                   .title("Title")
                                   .description("Desc")
                                   .price(100)
                                   .email("email@example.com")
                                   .authorFirstName("Ivan")
                                   .authorLastName("Ivanov")
                                   .phone("+7(901)239-24-15")
                                   .image("imageUrl")
                                   .build();

    Mockito.when(adService.getAd(1)).thenReturn(extendedAdDto);

    mockMvc.perform(get("/ads/1"))
     .andExpect(status().isOk())
     .andExpect(jsonPath("$.pk").value(1))
     .andExpect(jsonPath("$.title").value("Title"));
}

@Test
@WithMockUser(roles = "ADMIN")
void deleteAd_ShouldReturnOk() throws Exception {

    Mockito.doNothing().when(adService).deleteAd(1);

    mockMvc.perform(delete("/ads/1")
                     .with(csrf()))
     .andExpect(status().isOk());

    Mockito.verify(adService).deleteAd(1);
}

@Test
@WithMockUser(roles = "USER")
void updateAd_ShouldReturnUpdatedAdDto() throws Exception {
    CreateOrUpdateAd updateRequest = CreateOrUpdateAd.builder()
                                      .title("Updated Title")
                                      .price(150)
                                      .description("Updated Desc")
                                      .build();

    AdDto updatedAd = AdDto.builder()
                       .pk(1)
                       .title("Updated Title")
                       .price(150)
                       .author(1)
                       .image("imageUrl")
                       .build();

    Mockito.when(adService.updateAd(eq(1), any(CreateOrUpdateAd.class))).thenReturn(updatedAd);

    mockMvc.perform(patch("/ads/1")
                     .with(csrf())
                     .contentType(MediaType.APPLICATION_JSON)
                     .content(objectMapper.writeValueAsString(updateRequest)))
     .andExpect(status().isOk())
     .andExpect(jsonPath("$.title").value("Updated Title"))
     .andExpect(jsonPath("$.price").value(150));
}

@Test
@WithMockUser(roles = "USER")
void getMyAds_ReturnsUserAds() throws Exception {
    Ads myAds = Ads.builder()
                 .count(1)
                 .results(List.of(
                  AdDto.builder()
                   .pk(2)
                   .title("My Ad")
                   .price(200)
                   .author(2)
                   .image("myimage.jpg")
                   .build()
                 ))
                 .build();

    Mockito.when(adService.getMyAds()).thenReturn(myAds);

    mockMvc.perform(get("/ads/me"))
     .andExpect(status().isOk())
     .andExpect(jsonPath("$.count").value(1))
     .andExpect(jsonPath("$.results[0].title").value("My Ad"));
}
}