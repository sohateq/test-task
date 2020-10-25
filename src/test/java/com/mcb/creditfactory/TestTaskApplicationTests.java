package com.mcb.creditfactory;

import com.mcb.creditfactory.model.Car;
import com.mcb.creditfactory.repository.CarRepository;
import com.mcb.creditfactory.service.car.CarService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTaskApplicationTests {
    @Autowired
    private CarRepository carRepository;

    private Car testCar = new Car(TEST_ID, "Ford", "Model T", 12d, (short) 1980, BigDecimal.valueOf(1000));
    private static final Long TEST_ID = 123L; //does not matter, id is generating automatically by db

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
    public void test1() {
        System.out.println("Test car:" + testCar);
        Car savedCar = carRepository.save(testCar);
        System.out.println("Saved car:" + savedCar);

        Assert.assertTrue(savedCar.equalsIgnoreId(testCar));

    }

    @After
    public void afterTest() {
        carRepository.delete(testCar);
    }

}
