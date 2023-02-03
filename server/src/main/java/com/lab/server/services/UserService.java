package com.lab.server.services;

import com.lab.server.entities.User;
import com.lab.server.exceptions.UserAlreadyExistException;
import com.lab.server.exceptions.UserNotFoundException;
import com.lab.server.implementations.UserDetailsImp;
import com.lab.server.models.UserModel;
import com.lab.server.payload.LoginRequest;
import com.lab.server.payload.LoginResponse;
import com.lab.server.payload.SignUpRequest;
import com.lab.server.repositories.UserRepository;
import com.lab.server.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    @Autowired
    public UserService(AuthenticationManager authenticationManager, PasswordEncoder encoder, JwtUtils jwtUtils, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    public User registration(SignUpRequest sign) throws UserAlreadyExistException {
        User user = new User(sign.getUsername(), encoder.encode(sign.getPassword()));
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistException("Пользователь с таким именем уже существует");
        }
        return user;
    }

    public LoginResponse authorization(LoginRequest login) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImp userDetailsImp = (UserDetailsImp) authentication.getPrincipal();

        return new LoginResponse(jwt, userDetailsImp.getUsername());
    }

}
