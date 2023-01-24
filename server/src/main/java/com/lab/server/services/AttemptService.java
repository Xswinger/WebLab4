package com.lab.server.services;

import com.lab.server.AttemptValidator;
import com.lab.server.CoordinatesValidator;
import com.lab.server.entities.Attempt;
import com.lab.server.entities.Coordinates;
import com.lab.server.entities.User;
import com.lab.server.exceptions.InvalidCoordinatesException;
import com.lab.server.models.AttemptModel;
import com.lab.server.repositories.AttemptRepository;
import com.lab.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttemptService {

    private final AttemptRepository attemptRepository;

    private final UserRepository userRepository;

    @Autowired
    public AttemptService(AttemptRepository attemptRepository, UserRepository userRepository) {
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
    }

    public List<AttemptModel> createAttempt(Coordinates coordinates, Long userId) throws InvalidCoordinatesException {
        if (!CoordinatesValidator.validateCoordinates(coordinates)) {
            throw new InvalidCoordinatesException("Неверный набор координат");
        }

        boolean result = AttemptValidator.hitCheck(coordinates);

        User user = userRepository.findById(userId).get();
        Attempt attempt = new Attempt(user, coordinates, result);
        attemptRepository.save(attempt);
        return attemptRepository.findAllByOwner_Id(userId).stream().map(attempt1 -> AttemptModel.toModel(attempt1.getCoordinates(), attempt1.isResult())).toList();
    }

    public Long removeAttempt(Long attemptId) {
        return attemptId;
    }
}
