package ru.skypro.homework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.homework.service.ImageService;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageController.class)
public class ImageControllerTests {

@Autowired
private MockMvc mockMvc;

@MockBean
private ImageService imageService;

@Test
@WithMockUser(roles = "USER")
void updateUserImage_ShouldReturnOk() throws Exception {
    MockMultipartFile mockFile = new MockMultipartFile(
     "image",
     "test-image.jpg",
     MediaType.IMAGE_JPEG_VALUE,
     "fake-image-content".getBytes()
    );

    mockMvc.perform(MockMvcRequestBuilders.multipart("/users/me/image")
                     .file(mockFile)
                     .with(request -> {
                         request.setMethod("PATCH");
                         return request;
                     })
                     .with(csrf())
     )
     .andExpect(status().isOk());
}

@Test
@WithMockUser(roles = "USER")
void updateAdImage_ShouldReturnOk() throws Exception {
    MockMultipartFile mockFile = new MockMultipartFile(
     "image",
     "test-ad-image.png",
     MediaType.IMAGE_PNG_VALUE,
     "fake-image-content".getBytes()
    );

    mockMvc.perform(MockMvcRequestBuilders.multipart("/ads/1/image")
                     .file(mockFile)
                     .with(request -> {
                         request.setMethod("PATCH");
                         return request;
                     })
                     .with(csrf())
     )
     .andExpect(status().isCreated());
}
}