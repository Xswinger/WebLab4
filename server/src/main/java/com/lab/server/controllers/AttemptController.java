package com.lab.server.controllers;

import com.lab.server.entities.Coordinates;
import com.lab.server.exceptions.InvalidCoordinatesException;
import com.lab.server.services.AttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attempt")
public class AttemptController {

    @Autowired
    private final AttemptService attemptService;

    @Autowired
    public AttemptController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @PostMapping
    public ResponseEntity addAttempt(@RequestBody Coordinates coordinates, @RequestParam Long id) {
        try {
            return ResponseEntity.ok(attemptService.createAttempt(coordinates, id));
        } catch (InvalidCoordinatesException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity removeAttempt(@RequestParam Long attemptId) {
        try {
            return ResponseEntity.ok(attemptService.removeAttempt(attemptId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
