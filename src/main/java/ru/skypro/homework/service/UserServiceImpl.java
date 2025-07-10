package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.dto.NewPassword;

@Service
public class UserServiceImpl implements UserService{

    @Override
    public void updatePassword(NewPassword passwordDto) {
        // TODO: Реализация будет позже
        throw new UnsupportedOperationException("Метод updatePassword() не реализован");
    }

    @Override
    public User getUser() {
        // TODO: Реализация будет позже
        throw new UnsupportedOperationException("Метод getUser() не реализован");
    }

    @Override
    public void updateUser(UpdateUser updateDto) {
        // TODO: Реализация будет позже
        throw new UnsupportedOperationException("Метод updateUser() не реализован");
    }

    @Override
    public void updateUserImage(MultipartFile image) {
        // TODO: Реализация будет позже
        throw new UnsupportedOperationException("Метод updateUserImage() не реализован");
    }

}
