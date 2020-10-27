package com.mcb.creditfactory.service.car;

import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.external.CollateralObject;
import com.mcb.creditfactory.external.CollateralType;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;

@AllArgsConstructor
public class CarAdapter implements CollateralObject {
    private CarDto car;

    @Override
    public BigDecimal getValue() {
        if (car.getAssessments() == null || car.getAssessments().isEmpty()) return BigDecimal.ZERO;
        // return value with most actual date
        return car.getAssessments().stream().max(Comparator.comparing(o -> o.getSecond())).get().getFirst();
    }

    @Override
    public Short getYear() {
        return car.getYear();
    }

    @Override
    public LocalDate getDate() {
        // Для автомобилей дата оценки не используется, поэтому всегда берем текущую

        // В задании написано отрефакторить, поэтому я сделал как с самолетами - дату из dto
        if (car.getAssessments() == null || car.getAssessments().isEmpty()) return LocalDate.now();
        // return most actual date
        return car.getAssessments().stream().max(Comparator.comparing(Pair::getSecond)).get().getSecond();
    }

    @Override
    public CollateralType getType() {
        return CollateralType.CAR;
    }
}
