package com.mcb.creditfactory.utils;

import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AssessmentUtils {

    /**
     * Check equality for two List<Pair<BigDecimal, LocalDate>>, but compares BigDecimal with compareTo,
     * because BigDecimal.equals() works strange: BigDecimal.valueOf(10.00).equals(BigDecimal.valueOf(10)) is false!
     **/
    public static boolean assessmentEquals(List<Pair<BigDecimal, LocalDate>> as1, List<Pair<BigDecimal, LocalDate>> as2) {
        if (as1 == as2) return true;
        if (as1.size() != as2.size()) return false;
        for (int i = 0; i < as1.size(); i++) {
            if (as1.get(i).getFirst().compareTo(as2.get(i).getFirst()) != 0) return false;
            if (!as1.get(i).getSecond().equals(as2.get(i).getSecond())) return false;
        }
        return true;
    }
}
