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

    /**
     * Adds additional assessments for existing Collateral
     */
    public Collateral assess(Collateral object) {
        if (object instanceof CarDto) return assessCar((CarDto) object);
        if (object instanceof AirplaneDto) return assessAirplane((AirplaneDto) object);

        throw new IllegalArgumentException();
    }

    /**
     * Saves car if it was not saved before
     */
    private Long saveCar(CarDto car) {
        if (carService.existById(car.getId())) return null;

        boolean approved = carService.approve(car);
        if (!approved) return null;

        Long savedId = Optional.of(car)
                .map(carService::fromDto)
                .map(carService::save)
                .map(carService::getId)
                .orElse(null);

        boolean assessmentSaved = carService.saveAssessment(car, savedId);
        if (!assessmentSaved) return null;

        return savedId;
    }

    /**
     * Saves airplane if it was not saved before
     */
    private Long saveAirplane(AirplaneDto airplane) {
        if (airplaneService.existById(airplane.getId())) return null;

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

    /**
     * Saves car if info with assessment list
     */
    private Collateral getCarInfo(CarDto car) {
        return Optional.of(car)
                .map(carService::fromDto)
                .map(carService::getId)
                .flatMap(carService::load)
                .map(carService::toDTO)
                .orElse(null);
    }

    /**
     * Saves airplane if info with assessment list
     */
    private Collateral getAirplaneInfo(AirplaneDto airplane) {
        return Optional.of(airplane)
                .map(airplaneService::fromDto)
                .map(airplaneService::getId)
                .flatMap(airplaneService::load)
                .map(airplaneService::toDTO)
                .orElse(null);
    }

    private Collateral assessCar(CarDto car) {
        //no assessment for non existing car
        if (!carService.existById(car.getId())) return null;

        boolean saved = carService.saveAssessment(car, car.getId());
        if (!saved) return null;

        return getCarInfo(car);
    }

    private Collateral assessAirplane(AirplaneDto airplane) {
        //no assessment for non existing airplane
        if (!airplaneService.existById(airplane.getId())) return null;

        boolean saved = airplaneService.saveAssessment(airplane, airplane.getId());
        if (!saved) return null;

        return getAirplaneInfo(airplane);
    }
}
