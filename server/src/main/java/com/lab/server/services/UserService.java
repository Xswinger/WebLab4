package com.lab.server.services;

import com.lab.server.entities.User;
import com.lab.server.exceptions.UserAlreadyExistException;
import com.lab.server.exceptions.UserNotFoundException;
import com.lab.server.exceptions.WrongUserDataException;
import com.lab.server.models.UserModel;
import com.lab.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registration(User user) throws UserAlreadyExistException {
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new UserAlreadyExistException("Пользователь с таким именем уже существует");
        }
        return userRepository.save(user);
    }

    public boolean authorization(User user) throws WrongUserDataException {
        User foundedUser = userRepository.findByLogin(user.getLogin());
        if (foundedUser != null) {
            if (!Objects.equals(foundedUser.getPassword(), user.getPassword())) {
                throw new WrongUserDataException("Неверный пароль");
            } else {
                return true;
            }
        } else {
            throw new WrongUserDataException("Неверный логин");
        }
    }

    public UserModel getUser(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).get();
        if (user == null) {
            throw new UserNotFoundException("Пользователь не найден");
        } else {
            return UserModel.toModel(user);
        }
    }

    public Long removeUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }

}
