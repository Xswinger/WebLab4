package com.lab.server.controllers;

import com.lab.server.entities.User;
import com.lab.server.exceptions.UserNotFoundException;
import com.lab.server.implementations.UserDetailsImp;
import com.lab.server.payload.JwtResponse;
import com.lab.server.payload.LoginRequest;
import com.lab.server.payload.SignUpRequest;
import com.lab.server.repositories.UserRepository;
import com.lab.server.security.JwtUtils;
import com.lab.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    @Autowired
    public UserController(UserRepository userRepository, AuthenticationManager authenticationManager, UserService userService, JwtUtils jwtUtils, PasswordEncoder encoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
    }
    @PostMapping("/auth")
    public ResponseEntity authorization(@Valid @RequestBody LoginRequest login) {
        try{
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImp userDetailsImp = (UserDetailsImp) authentication.getPrincipal();
//
//            User user = new User(login.getUsername(), encoder.encode(login.getPassword()));
//            userService.authorization(user);
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetailsImp.getUsername()));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping("/reg")
    public ResponseEntity registration(@Valid @RequestBody SignUpRequest sign) {
        try {
            if(userRepository.findByLogin(sign.getUsername()) != null) {
                return ResponseEntity.badRequest().body("Ошибка - пользователь уже зарегистрирован");
            }
            User user = new User(sign.getUsername(), encoder.encode(sign.getPassword()));
            userService.registration(user);
            return ResponseEntity.ok("Пользователь успешно авторизован");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping
    public ResponseEntity getUser(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(userService.getUser(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.removeUser(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
