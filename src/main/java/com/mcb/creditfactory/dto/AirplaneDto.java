package com.mcb.creditfactory.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
}
