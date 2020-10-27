package com.mcb.creditfactory.service.airplane;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.model.Airplane;
import com.mcb.creditfactory.model.AirplaneAssessment;

import java.util.Optional;

public interface AirplaneService {
    boolean approve(AirplaneDto dto);
    Airplane save(Airplane airplane);
    Boolean saveAssessment(AirplaneDto airplane, Long savedAirplaneId);
    Optional<Airplane> load(Long id);
    Airplane fromDto(AirplaneDto dto);
    AirplaneDto toDTO(Airplane airplane);
    Long getId(Airplane airplane);
    boolean existById(Long id);
}
