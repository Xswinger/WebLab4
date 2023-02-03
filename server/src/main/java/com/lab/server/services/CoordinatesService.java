package com.lab.server.services;

import com.lab.server.CoordinatesValidator;
import com.lab.server.entities.Coordinates;
import com.lab.server.entities.User;
import com.lab.server.exceptions.InvalidCoordinatesException;
import com.lab.server.models.CoordinatesModel;
import com.lab.server.repositories.CoordinatesRepository;
import com.lab.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoordinatesService {
    private final CoordinatesRepository coordinatesRepository;

    private final UserRepository userRepository;

    private final CoordinatesValidator validator;

    @Autowired
    public CoordinatesService(CoordinatesRepository coordinatesRepository, UserRepository userRepository, CoordinatesValidator validator) {
        this.coordinatesRepository = coordinatesRepository;
        this.userRepository = userRepository;
        this.validator = validator;
    }

    public CoordinatesModel createCoordinates(Coordinates coordinates, Long userId) throws InvalidCoordinatesException {
        if (!validator.validateCoordinates(coordinates)) {
            throw new InvalidCoordinatesException("Неверный набор координат");
        }

        User user = userRepository.findById(userId).get();
        return CoordinatesModel.toModel(coordinatesRepository.save(coordinates));
    }

    public Long removeCoordinates(Long coordinatesId) {
        coordinatesRepository.deleteById(coordinatesId);
        return coordinatesId;
    }
}
