package com.mcb.creditfactory;

import com.mcb.creditfactory.controller.CollateralObjectController;
import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.model.Airplane;
import com.mcb.creditfactory.model.Car;
import com.mcb.creditfactory.repository.AirplaneRepository;
import com.mcb.creditfactory.repository.CarRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTaskApplicationTests {
    @Autowired
    private CollateralObjectController controller;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private AirplaneRepository airplaneRepository;

    private Car testCar = new Car(TEST_ID, "Ford", "Model T", 12d, (short) 1980, BigDecimal.valueOf(1000));
    private static final Long TEST_ID = 123L; //does not matter, id is generating automatically by db

    private Airplane testAirplane =
            new Airplane(TEST_ID, "Boeing", "Boeing-777", "USA", (short) 2005, 15, 200);

    private AirplaneDto testAirplaneDto =
            new AirplaneDto(1L, "Boeing", "Boeing-777", "USA", (short) 2005, 15, 200);

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
        System.out.println("Test car: " + testCar);
        Car savedCar = carRepository.save(testCar);
        System.out.println("Saved car: " + savedCar);

        Assert.assertTrue(savedCar.equalsIgnoreId(testCar));
    }

    @Test
    public void testSaveAirplane() {
        System.out.println("Test airplane: " + testAirplane);
        Airplane savedAirplane = airplaneRepository.save(testAirplane);
        System.out.println("Saved airplane: " + savedAirplane);

        Assert.assertTrue(savedAirplane.equalsIgnoreId(testAirplane));
    }

    @After
    public void afterTest() {
        carRepository.delete(testCar);
    }

    @Test
    public void testSaveAndLoadAirplane() throws InterruptedException {
        System.out.println("Test airplaneDto: " + testAirplaneDto);
        controller.save(testAirplaneDto);

        HttpEntity<Collateral> httpEntity = controller.getInfo(testAirplaneDto);
        System.out.println("Loaded airplane : " + httpEntity.getBody());
    }

}
