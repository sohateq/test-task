package com.mcb.creditfactory.service;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.service.airplane.AirplaneService;
import com.mcb.creditfactory.service.car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CollateralService {
    @Autowired
    private CarService carService;

    @Autowired
    private AirplaneService airplaneService;

    public Long saveCollateral(Collateral object) {
        if (object instanceof CarDto) return saveCar((CarDto) object);
        if (object instanceof AirplaneDto) return saveAirplane((AirplaneDto) object);

        throw new IllegalArgumentException();
    }

    public Collateral getInfo(Collateral object) {
        if (object instanceof CarDto) return getCarInfo((CarDto) object);
        if (object instanceof AirplaneDto) return getAirplaneInfo((AirplaneDto) object);

        throw new IllegalArgumentException();
    }

    private Long saveCar(CarDto car) {
        //todo add assess id


        boolean approved = carService.approve(car);
        if (!approved) return null;

        return Optional.of(car)
                .map(carService::fromDto)
                .map(carService::save)
                .map(carService::getId)
                .orElse(null);
    }

    private Long saveAirplane(AirplaneDto airplane) {
        if (airplaneService.existById(airplane.getId())) return  null;

        boolean approved = airplaneService.approve(airplane);
        if (!approved) return null;

        Long savedId = Optional.of(airplane)
                .map(airplaneService::fromDto)
                .map(airplaneService::save)
                .map(airplaneService::getId)
                .orElse(null);

        boolean assessmentSaved = airplaneService.saveAssessment(airplane, savedId);
        if (!assessmentSaved) return null;

        return savedId;
    }

    private Collateral getCarInfo(CarDto car) {
        return Optional.of(car)
                .map(carService::fromDto)
                .map(carService::getId)
                .flatMap(carService::load)
                .map(carService::toDTO)
                .orElse(null);
    }

    private Collateral getAirplaneInfo(AirplaneDto airplane) {
        return Optional.of(airplane)
                .map(airplaneService::fromDto)
                .map(airplaneService::getId)
                .flatMap(airplaneService::load)
                .map(airplaneService::toDTO)
                .orElse(null);
    }
}
