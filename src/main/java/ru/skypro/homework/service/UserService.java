package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;

public interface UserService {

    void updatePassword(NewPassword passwordDto);

    UserDto getUser(); // новое имя

    void updateUser(UpdateUser updateDto); // новое имя

}
