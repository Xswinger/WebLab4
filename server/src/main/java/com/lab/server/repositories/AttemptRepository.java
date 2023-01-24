package com.lab.server.repositories;

import com.lab.server.entities.Attempt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptRepository extends CrudRepository<Attempt, Long> {
     List<Attempt> findAllByOwner_Id(Long id);
}
