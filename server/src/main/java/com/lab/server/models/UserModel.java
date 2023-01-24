package com.lab.server.models;

import com.lab.server.entities.User;

import java.util.List;

public class UserModel {
    private Long id;
    private String Username;
    private List<CoordinatesModel> coordinates;

    public UserModel() {
    }

    public static UserModel toModel(User entity) {
        UserModel model = new UserModel();
        model.setId(entity.getId());
        model.setUsername(entity.getLogin());
//        model.setCoordinates(entity.getCoordinatesList().stream().map(CoordinatesModel::toModel).collect(Collectors.toList()));
        return model;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CoordinatesModel> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<CoordinatesModel> coordinates) {
        this.coordinates = coordinates;
    }
}
