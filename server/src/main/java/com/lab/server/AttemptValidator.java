package com.lab.server;

import com.lab.server.entities.Coordinates;
import org.springframework.stereotype.Service;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

@Service
public class AttemptValidator {

    public boolean hitCheck(Coordinates coordinates) {
        return (secondQuarterHitCheck(coordinates) || thirdQuarterHitCheck(coordinates)
                || fourQuarterHitCheck(coordinates));
    }

    private boolean secondQuarterHitCheck(Coordinates coordinates) {
        return ((coordinates.getR()/2) >= coordinates.getY() && coordinates.getX() <= 0 && coordinates.getY() >= 0
                && coordinates.getX() >= (-coordinates.getR()));
    }

    private boolean thirdQuarterHitCheck(Coordinates coordinates) {
        return (coordinates.getX() <= 0 && coordinates.getY() <= 0
                && coordinates.getY() >= (- coordinates.getX() - (coordinates.getR()/2)));
    }

    private boolean fourQuarterHitCheck(Coordinates coordinates) {
        return ((pow(coordinates.getX(), 2) + pow(coordinates.getY(), 2) <= coordinates.getR())
                && coordinates.getX() >= 0 && coordinates.getY() <= 0);
    }

}
