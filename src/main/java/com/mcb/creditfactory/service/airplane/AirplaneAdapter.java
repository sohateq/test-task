package com.mcb.creditfactory.service.airplane;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.external.CollateralObject;
import com.mcb.creditfactory.external.CollateralType;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;

@AllArgsConstructor
public class AirplaneAdapter implements CollateralObject {
    private AirplaneDto airplane;

    @Override
    public BigDecimal getValue() {
        if (airplane.getAssessments() == null || airplane.getAssessments().isEmpty()) return BigDecimal.ZERO;
        // return value with most actual date
        return airplane.getAssessments().stream().max(Comparator.comparing(o -> o.getSecond())).get().getFirst();
    }

    @Override
    public Short getYear() {
        return airplane.getYear();
    }

    @Override
    public LocalDate getDate() {
        if (airplane.getAssessments() == null || airplane.getAssessments().isEmpty()) return LocalDate.now();
        // return most actual date
        return airplane.getAssessments().stream().max(Comparator.comparing(Pair::getSecond)).get().getSecond();
    }

    @Override
    public CollateralType getType() {
        return CollateralType.AIRPLANE;
    }
}
