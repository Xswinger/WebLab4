package com.lab.server.repositories;

import com.lab.server.entities.Attempt;
import com.lab.server.models.AttemptModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Long> {
     List<Attempt> findAllByOwner_Id(Long id);


}
