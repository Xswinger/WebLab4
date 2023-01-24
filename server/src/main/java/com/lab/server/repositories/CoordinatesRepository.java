package com.lab.server.repositories;

import com.lab.server.entities.Coordinates;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatesRepository extends CrudRepository<Coordinates, Long> {
}
