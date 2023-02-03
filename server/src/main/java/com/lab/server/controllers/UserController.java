package com.lab.server.controllers;

import com.lab.server.payload.*;
import com.lab.server.postProcessing.Audited;
import com.lab.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserController() {
    }

    @PostMapping("/auth")
    @Audited
    public ResponseEntity<Object> authorization(@Valid @RequestBody LoginRequest login) {
        try{
            return ResponseEntity.ok(userService.authorization(login));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Ошибка: неверные данные для входа"));
        }
    }
    @PostMapping("/reg")
    public ResponseEntity<SignUpResponse> registration(@Valid @RequestBody SignUpRequest sign) {
        try {
            userService.registration(sign);
            return ResponseEntity.ok(new SignUpResponse("Пользователь успешно авторизован"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new SignUpResponse(e.getMessage()));
        }
    }
}
