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
@JsonTypeName("airplane")
public class AirplaneDto implements Collateral {
    private Long id;
    private String brand;
    private String model;
    private String manufacturer;
    private Short year;
    private Integer fuelCapacity;
    private Integer seats;
    private List<Pair<BigDecimal, LocalDate>> assessments;

    /**
     * Like standard equals but indifferent fot id field.
     * Helpful for tests
     **/
    public boolean equalsIgnoreId(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirplaneDto that = (AirplaneDto) o;
        return Objects.equals(brand, that.brand) &&
                Objects.equals(model, that.model) &&
                Objects.equals(manufacturer, that.manufacturer) &&
                Objects.equals(year, that.year) &&
                Objects.equals(fuelCapacity, that.fuelCapacity) &&
                Objects.equals(seats, that.seats) &&
                AssessmentUtils.assessmentEquals(assessments, that.assessments);
    }

}
