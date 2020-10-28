package com.mcb.creditfactory.service.car;

import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.external.ExternalApproveService;
import com.mcb.creditfactory.model.Car;
import com.mcb.creditfactory.model.CarAssessment;
import com.mcb.creditfactory.repository.CarAssessmentRepository;
import com.mcb.creditfactory.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private ExternalApproveService approveService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarAssessmentRepository carAssessmentRepository;

    @Override
    public boolean approve(CarDto dto) {
        return approveService.approve(new CarAdapter(dto)) == 0;
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Boolean saveAssessment(CarDto car, Long savedCarId) {
        boolean isSaved = true;

        CarAssessment assessment = new CarAssessment();
        assessment.setId(0L); //autogenerated
        assessment.setIdCar(savedCarId);

        for (Pair<BigDecimal, LocalDate> pair : car.getAssessments()) {
            assessment.setValue(pair.getFirst());
            assessment.setDate(pair.getSecond());

            if (carAssessmentRepository.save(assessment).getId() == null) isSaved = false;
        }

        return isSaved;
    }

    @Override
    public Optional<Car> load(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public Car fromDto(CarDto dto) {
        return new Car(
                dto.getId(),
                dto.getBrand(),
                dto.getModel(),
                dto.getPower(),
                dto.getYear()
        );
    }

    @Override
    public CarDto toDTO(Car car) {
        //get assessment list from repository
        List<Pair<BigDecimal, LocalDate>> pairList =
                carAssessmentRepository.findAllByIdCar(car.getId())
                        .stream()
                        .map(CarAssessment::getPair)
                        .collect(Collectors.toList());

        return new CarDto(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getPower(),
                car.getYear(),
                pairList
        );
    }

    @Override
    public Long getId(Car car) {
        return car.getId();
    }

    @Override
    public boolean existById(Long id) {
        return carRepository.existsById(id);
    }
}
