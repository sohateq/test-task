package com.mcb.creditfactory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AIRPLANE")
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private String manufacturer;

    @Column(name = "year_of_issue")
    private Short year;

    private Integer fuelCapacity;
    private Integer seats;

    public boolean equalsIgnoreId(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airplane airplane = (Airplane) o;
        return Objects.equals(brand, airplane.brand) &&
                Objects.equals(model, airplane.model) &&
                Objects.equals(manufacturer, airplane.manufacturer) &&
                Objects.equals(year, airplane.year) &&
                Objects.equals(fuelCapacity, airplane.fuelCapacity) &&
                Objects.equals(seats, airplane.seats);
    }
}
