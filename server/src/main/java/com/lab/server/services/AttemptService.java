package com.lab.server.services;

import com.lab.server.AttemptValidator;
import com.lab.server.CoordinatesValidator;
import com.lab.server.entities.Attempt;
import com.lab.server.entities.Coordinates;
import com.lab.server.entities.User;
import com.lab.server.exceptions.InvalidCoordinatesException;
import com.lab.server.models.AttemptModel;
import com.lab.server.payload.DataResponse;
import com.lab.server.payload.JwtRequest;
import com.lab.server.payload.JwtResponse;
import com.lab.server.repositories.AttemptRepository;
import com.lab.server.repositories.UserRepository;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttemptService {
    private final AttemptRepository attemptRepository;

    private final UserRepository userRepository;

    private final CoordinatesValidator validateCoordinates;

    private final AttemptValidator validatorAttempt;

    @Autowired
    public AttemptService(AttemptRepository attemptRepository, UserRepository userRepository, CoordinatesValidator validateCoordinates, AttemptValidator validatorAttempt) {
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
        this.validateCoordinates = validateCoordinates;
        this.validatorAttempt = validatorAttempt;
    }

    public Object createAttempt(JwtRequest request) throws JwtException, InvalidCoordinatesException {

        Coordinates coordinates = new Coordinates(request.getX(), request.getY(), request.getR());
        if (!request.isCanvasRequest()) {
            if (!validateCoordinates.validateCoordinates(coordinates)) {
                throw new InvalidCoordinatesException("Неверный набор координат");
            }
        }

        boolean result = validatorAttempt.hitCheck(coordinates);

        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findByLogin(userDetails.getUsername());
        Attempt newAttempt = new Attempt(user, coordinates, result);
        attemptRepository.save(newAttempt);
        return new JwtResponse(attemptRepository.findAllByOwner_Id(user.getId()).stream().map(attempt -> AttemptModel.toModel(attempt.getCoordinates(), attempt.isResult())).toList());
    }

    public DataResponse getAllAttempt() {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByLogin(userDetails.getUsername());
        List<AttemptModel> list = attemptRepository.findAllByOwner_Id(user.getId()).stream().map(attempt -> AttemptModel.toModel(attempt.getCoordinates(), attempt.isResult())).collect(Collectors.toList());
        return new DataResponse(list);
    }
}
