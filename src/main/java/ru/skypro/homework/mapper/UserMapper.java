package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;

@Component
public class UserMapper {

    // Метод для конвертации User в UserDto
    public UserDto userIntoUserDto(User user){
        return new UserDto(user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getRole(),
                user.getImage() == null? null: ("/images/" + user.getImage().getId()));
    }

    //Метод для конвертации UpdateUser в User
    public User updateUserIntoUser(User user, UpdateUser updateUser){
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setPhone(user.getPhone());
        return user;
    }

    //Метод для конвертации User в UpdateUser
    public UpdateUser userIntoUpdateUser(User user){
        return new UpdateUser(user.getFirstName(), user.getLastName(), user.getPhone());
    }
}
