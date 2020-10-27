package com.mcb.creditfactory.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mcb.creditfactory.utils.AssessmentUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("car")
public class CarDto implements Collateral {
    private Long id;
    private String brand;
    private String model;
    private Double power;
    private Short year;
    private List<Pair<BigDecimal, LocalDate>> assessments;

    /**
     * Like standard equals but indifferent fot id field.
     * Helpful for tests
     **/
    public boolean equalsIgnoreId(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDto carDto = (CarDto) o;
        return Objects.equals(brand, carDto.brand) &&
                Objects.equals(model, carDto.model) &&
                Objects.equals(power, carDto.power) &&
                Objects.equals(year, carDto.year) &&
                AssessmentUtils.assessmentEquals(assessments, carDto.assessments);
    }
}
