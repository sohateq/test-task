package com.mcb.creditfactory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AIRPLANE_ASSESSMENT")
public class AirplaneAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessment_id")
    private Long id;

    @Column(name = "airplane_id")
    private Long idAirplane;

    private BigDecimal value;
    private LocalDate date;

    public Pair<BigDecimal, LocalDate> getPair() {
        return Pair.of(value, date);
    }
}
