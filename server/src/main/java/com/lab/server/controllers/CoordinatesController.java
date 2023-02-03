package com.lab.server.controllers;


import com.lab.server.entities.Coordinates;
import com.lab.server.exceptions.InvalidCoordinatesException;
import com.lab.server.services.CoordinatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coordinates")
public class CoordinatesController {

    private final CoordinatesService coordinatesService;

    @Autowired
    public CoordinatesController(CoordinatesService coordinatesService) {
        this.coordinatesService = coordinatesService;
    }

    @PostMapping
    public ResponseEntity<?> createCoordinates(@RequestBody Coordinates coordinates, @RequestParam Long userId) {
        try {
            return ResponseEntity.ok(coordinatesService.createCoordinates(coordinates, userId));
        } catch (InvalidCoordinatesException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла непредвиденная ошибка");
        }
    }
}
