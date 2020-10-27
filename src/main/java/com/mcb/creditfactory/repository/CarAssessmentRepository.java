package com.mcb.creditfactory.repository;

import com.mcb.creditfactory.model.CarAssessment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarAssessmentRepository extends CrudRepository<CarAssessment, Long> {
    List<CarAssessment> findAllByIdCar(Long idCar);
}
