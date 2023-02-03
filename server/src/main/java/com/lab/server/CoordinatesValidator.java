package com.lab.server;

import com.lab.server.entities.Coordinates;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordinatesValidator {
    public boolean validateCoordinates(Coordinates coordinates) {
        return (validateX(coordinates.getX()) && validateY(coordinates.getY()) && validateR(coordinates.getR()));
    }

    private boolean validateX(double x) {
        return (List.of(-3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0).contains(x));
    }

    private boolean validateY(double y) {
        return (y > -5 && y < 5);
    }

    private boolean validateR(double r) {
        return (List.of(-3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0).contains(r));
    }
}
