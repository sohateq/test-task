package com.mcb.creditfactory;

import com.mcb.creditfactory.controller.CollateralObjectController;
import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.repository.AirplaneRepository;
import com.mcb.creditfactory.repository.CarRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private AirplaneRepository airplaneRepository;



    private CarDto testValidCar = new CarDto(0L, "Ford", "Focus", 12d, (short) 2005, createValidListForCar());
    private CarDto testInvalidCar = new CarDto(0L, "Ford", "Model T", 12d, (short) 1927, createInvalidListForCar());


    private List<Pair<BigDecimal, LocalDate>> listForAirplane = createValidListForAirplane();

    private AirplaneDto testAirplaneDto =
            new AirplaneDto(0L, "Boeing", "Boeing-777", "USA", (short) 2005, 15, 200, listForAirplane);



    @Test
    public void contextLoads() {
    }

//    @Test
//    public void test0() {
//        System.out.println("hello test");
//        System.out.println(testCar.toString());
//        List<Car> myList = Lists.newArrayList(carRepository.findAll());
//        myList.forEach(System.out::println);
//        System.out.println("bye test");

    //    }

    @Test
    public void testSaveCar() {
//        System.out.println("Test car: " + testValidCar);
//        Car savedCar = carRepository.save(testValidCar);
//        System.out.println("Saved car: " + savedCar);
//
//        Assert.assertTrue(savedCar.equalsIgnoreId(testValidCar));
    }

    @Test
    public void testSaveAirplane() {
//        System.out.println("Test airplane: " + testAirplane);
//        Airplane savedAirplane = airplaneRepository.save(testAirplane);
//        System.out.println("Saved airplane: " + savedAirplane);
//
//        Assert.assertTrue(savedAirplane.equalsIgnoreId(testAirplane));
    }

    @Test
    public void testLoadAirplane() {
        System.out.println("Test airplaneDto: " + testAirplaneDto);
        HttpEntity<Long> httpId = controller.save(testAirplaneDto);
        testAirplaneDto.setId(httpId.getBody());

        HttpEntity<Collateral> httpEntity = controller.getInfo(testAirplaneDto);
        System.out.println("Loaded airplane : " + httpEntity.getBody());
    }

    @Test
    public void testAssessAirplane() {
        //save airplane
        AirplaneDto airplaneDto = createAirplaneDto(createValidListForAirplane());
        System.out.println("Add airplaneDto: " + airplaneDto);
        HttpEntity<Long> httpId = controller.save(airplaneDto);
        airplaneDto.setId(httpId.getBody());

        //load airplane
        HttpEntity<Collateral> httpEntity = controller.getInfo(airplaneDto);
        System.out.println("Loaded airplane : " + httpEntity.getBody());

        //additional assess
        airplaneDto.setAssessments(createInvalidListForAirplane());
        System.out.println("Assess airplaneDto: " + airplaneDto);
        httpEntity = controller.assess(airplaneDto);
        System.out.println("Loaded updated airplane : " + httpEntity.getBody());
    }

    private AirplaneDto createAirplaneDto(List<Pair<BigDecimal, LocalDate>> assessment) {
        return new AirplaneDto(-1L, "Boeing", "Boeing-777", "USA", (short) 2005, 15, 200, assessment);
    }

    private List<Pair<BigDecimal, LocalDate>> createValidListForAirplane() {
        BigDecimal minValue = BigDecimal.valueOf(230000000);

        List<Pair<BigDecimal, LocalDate>> list = new ArrayList<>();
        list.add(Pair.of(minValue, LocalDate.of(2018, Month.OCTOBER, 1)));
        list.add(Pair.of(BigDecimal.valueOf(210000000), LocalDate.of(2016, Month.OCTOBER, 1))); //outdated
        return list;
    }

    private List<Pair<BigDecimal, LocalDate>> createInvalidListForAirplane() {
        List<Pair<BigDecimal, LocalDate>> list = new ArrayList<>();
        list.add(Pair.of(BigDecimal.valueOf(200000000), LocalDate.of(2015, Month.OCTOBER, 1))); //invalid
        return list;
    }

    private List<Pair<BigDecimal, LocalDate>> createValidListForCar() {
        List<Pair<BigDecimal, LocalDate>> list = new ArrayList<>();
        list.add(Pair.of(BigDecimal.valueOf(1000000), LocalDate.of(2020, Month.OCTOBER, 1)));
        list.add(Pair.of(BigDecimal.valueOf(900000), LocalDate.of(2010, Month.OCTOBER, 1))); //invalid
        return list;
    }

    private List<Pair<BigDecimal, LocalDate>> createInvalidListForCar() {
        List<Pair<BigDecimal, LocalDate>> list = new ArrayList<>();
        list.add(Pair.of(BigDecimal.valueOf(900000), LocalDate.of(2010, Month.OCTOBER, 1))); //invalid
        return list;
    }
}
