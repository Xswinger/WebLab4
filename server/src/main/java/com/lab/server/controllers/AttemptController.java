package com.lab.server.controllers;

import com.lab.server.payload.JwtRequest;
import com.lab.server.services.AttemptService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attempt")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AttemptController {

    Logger logger = LoggerFactory.getLogger(AttemptController.class);

    private final AttemptService attemptService;

    @Autowired
    public AttemptController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addAttempt(@Valid @RequestBody JwtRequest request) {
        try {
            return ResponseEntity.ok(attemptService.createAttempt(request));
        } catch (Exception e) {
            logger.error(request.getX().toString());
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e.getMessage());
        }
    }

    @PostMapping("/getAll")
    public ResponseEntity<Object> getAllAttempt() {
        try {
            return ResponseEntity.ok(attemptService.getAllAttempt());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e.getMessage());
        }
    }
}
