package com.mcb.creditfactory;

import com.mcb.creditfactory.controller.CollateralObjectController;
import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTaskApplicationTests {

    @Autowired
    private CollateralObjectController controller;

    private CarDto testValidCar = new CarDto(0L, "Ford", "Focus", 12d, (short) 2005, createValidListForCar());
    private CarDto testInvalidCar = new CarDto(0L, "Ford", "Model T", 12d, (short) 1927, createInvalidListForCar());

    private AirplaneDto testValidAirplane =
            new AirplaneDto(0L, "Boeing", "Boeing-777", "USA", (short) 2005, 15, 200, createValidListForAirplane());



    /**
     * Tests that controller.save saves valid Airplane
     */
    @Test
    public void testSaveAirplane() {
        System.out.println("Test airplane: " + testValidAirplane);
        HttpEntity<Long> httpId = controller.save(testValidAirplane);
        System.out.println("Saved id: " + httpId.getBody());
        Assert.assertNotNull(httpId.getBody());
    }

    /**
     * Tests that controller.save will not save Airplane if id is already in database
     */
    @Test
    public void testSaveAirplaneTwice() {
        HttpEntity<Long> httpId = controller.save(testValidAirplane);

        testValidAirplane.setId(httpId.getBody());
        HttpEntity<Long> httpIdSecond = controller.save(testValidAirplane);

        Assert.assertNull(httpIdSecond.getBody());
    }

    /**
     * Tests that controller.save will not save invalid for ExternalApproveService Airplane
     */
    @Test
    public void testSaveInvalidAirplane() {
        AirplaneDto invalidAirplane = createAirplaneDto(createInvalidListForAirplane());
        HttpEntity<Long> httpId = controller.save(invalidAirplane);

        Assert.assertNull(httpId.getBody());
    }

    /**
     * Tests that controller.getInfo provides saved Airplane in one piece
     */
    @Test
    public void testGetInfoAirplane() {
        //save AirplaneDto
        System.out.println("Test airplaneDto: " + testValidAirplane);
        HttpEntity<Long> httpId = controller.save(testValidAirplane);

        AirplaneDto savedDto = testValidAirplane;
        savedDto.setId(httpId.getBody());

        //load saved Airplane
        HttpEntity<Collateral> httpEntity = controller.getInfo(savedDto);
        System.out.println("Loaded airplane : " + httpEntity.getBody());

        Assert.assertTrue(testValidAirplane.equalsIgnoreId(httpEntity.getBody()));
    }

    /**
     * Tests that controller.assess writes additional assessments to database
     */
    @Test
    public void testAssessAirplane() {
        //save valid airplane
        AirplaneDto airplaneDto = createAirplaneDto(createValidListForAirplane());
        int assessmentsCount = airplaneDto.getAssessments().size();
        HttpEntity<Long> httpId = controller.save(airplaneDto);
        //set right Id for airplane
        airplaneDto.setId(httpId.getBody());

        //load airplane
        HttpEntity<Collateral> httpEntity = controller.getInfo(airplaneDto);

        //additional assess
        airplaneDto.setAssessments(createInvalidListForAirplane());
        assessmentsCount += airplaneDto.getAssessments().size();
        httpEntity = controller.assess(airplaneDto);
        System.out.println("Loaded updated airplane : " + httpEntity.getBody());
        Assert.assertTrue(assessmentsCount == (((AirplaneDto)httpEntity.getBody()).getAssessments().size()));
    }

    /**
     * Creates AirplaneDto with given assessment list
     *
     * @param assessment - assessment list for dto
     */
    private AirplaneDto createAirplaneDto(List<Pair<BigDecimal, LocalDate>> assessment) {
        return new AirplaneDto(-1L, "Boeing", "Boeing-777", "USA", (short) 2005, 15, 200, assessment);
    }

    /**
     * @return assessment list for airplanes valid for ExternalApproveService
     */
    private List<Pair<BigDecimal, LocalDate>> createValidListForAirplane() {
        BigDecimal minValue = BigDecimal.valueOf(230000000);

        List<Pair<BigDecimal, LocalDate>> list = new ArrayList<>();
        list.add(Pair.of(minValue, LocalDate.of(2018, Month.OCTOBER, 1)));
        list.add(Pair.of(BigDecimal.valueOf(210000000), LocalDate.of(2016, Month.OCTOBER, 1))); //outdated
        return list;
    }

    /**
     * @return assessment list for airplanes invalid for ExternalApproveService
     */
    private List<Pair<BigDecimal, LocalDate>> createInvalidListForAirplane() {
        List<Pair<BigDecimal, LocalDate>> list = new ArrayList<>();
        list.add(Pair.of(BigDecimal.valueOf(200000000), LocalDate.of(2015, Month.OCTOBER, 1))); //invalid
        return list;
    }

    /**
     * @return assessment list for cars valid for ExternalApproveService
     */
    private List<Pair<BigDecimal, LocalDate>> createValidListForCar() {
        List<Pair<BigDecimal, LocalDate>> list = new ArrayList<>();
        list.add(Pair.of(BigDecimal.valueOf(1000000), LocalDate.of(2020, Month.OCTOBER, 1)));
        list.add(Pair.of(BigDecimal.valueOf(900000), LocalDate.of(2010, Month.OCTOBER, 1))); //invalid
        return list;
    }

    /**
     * @return assessment list for cars invalid for ExternalApproveService
     */
    private List<Pair<BigDecimal, LocalDate>> createInvalidListForCar() {
        List<Pair<BigDecimal, LocalDate>> list = new ArrayList<>();
        list.add(Pair.of(BigDecimal.valueOf(900000), LocalDate.of(2010, Month.OCTOBER, 1))); //invalid
        return list;
    }
}
