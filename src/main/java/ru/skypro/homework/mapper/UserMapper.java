package ru.skypro.homework.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;

/**
 * Маппер для преобразования между сущностью User и DTO
 */
@Component
public class UserMapper{

@Value("${path.images}")
private  String images;

/**
 * Преобразование User в UserDto
 * @param user сущность пользователя
 * @return DTO пользователя
 */
public UserDto userIntoUserDto(User user){
    return new UserDto(user.getId(),
     user.getEmail(),
     user.getFirstName(),
     user.getLastName(),
     user.getPhone(),
     user.getRole(),
     user.getImage()==null?null:(images +user.getImage().getId()));
}

/**
 * Обновление сущности User из UpdateUser
 * @param user сущность пользователя для обновления
 * @param updateUser DTO с новыми данными
 * @return обновленная сущность пользователя
 */
public User updateUserIntoUser(User user,UpdateUser updateUser){
    user.setFirstName(updateUser.getFirstName());
    user.setLastName(updateUser.getLastName());
    user.setPhone(updateUser.getPhone());
    return user;
}

/**
 * Преобразование UserDto в User
 * @param userDto DTO пользователя
 * @return сущность пользователя
 */
public User userDtoIntoUser(UserDto userDto){
    User user=new User();
    user.setId(userDto.getId());
    user.setEmail(userDto.getEmail());
    user.setFirstName(userDto.getFirstName());
    user.setLastName(userDto.getLastName());
    user.setPhone(userDto.getPhone());
    user.setRole(userDto.getRole());
    return user;

}

/**
 * Преобразование User в UpdateUser
 * @param user сущность пользователя
 * @return DTO для обновления пользователя
 */
public UpdateUser userIntoUpdateUser(User user){
    return new UpdateUser(user.getFirstName(),user.getLastName(),user.getPhone());
}
}



