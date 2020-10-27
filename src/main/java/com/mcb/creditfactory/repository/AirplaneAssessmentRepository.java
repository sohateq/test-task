package com.mcb.creditfactory.repository;

import com.mcb.creditfactory.model.AirplaneAssessment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AirplaneAssessmentRepository extends CrudRepository<AirplaneAssessment, Long> {
    List<AirplaneAssessment> findAllByIdAirplane(Long idAirplane);
}
