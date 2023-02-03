package com.lab.server.payload;
import com.lab.server.models.AttemptModel;

import java.io.Serializable;
import java.util.List;


public class DataResponse implements Serializable {
    private List<AttemptModel> coordinates;

    public DataResponse(List<AttemptModel> coordinates) {
        this.coordinates = coordinates;
    }

    public List<AttemptModel> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<AttemptModel> coordinates) {
        this.coordinates = coordinates;
    }

}
