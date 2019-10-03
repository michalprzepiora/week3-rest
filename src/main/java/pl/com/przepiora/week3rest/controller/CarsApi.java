package pl.com.przepiora.week3rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.przepiora.week3rest.model.Car;
import pl.com.przepiora.week3rest.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarsApi {

    private CarService carService;

    @Autowired
    public CarsApi(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Car>> getCarsByColor(@PathVariable String color) {
        return carService.getCarsByColor(color);
    }

    @PostMapping
    public void addCar(@RequestParam String mark,
                       @RequestParam String model, @RequestParam String color) {
        carService.addCar(mark, model, color);
    }

    @PutMapping("{id}")
    public ResponseEntity<Car> replaceCarById(@PathVariable Long id, @RequestParam String mark,
                                              @RequestParam String model, @RequestParam String color) {
        return carService.replaceCarById(id, mark, model, color);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Car> replaceCarFieldById(@PathVariable Long id,
                                                   @RequestParam String fieldName, @RequestParam String fieldValue) {
        return carService.replaceCarFieldById(id, fieldName, fieldValue);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Car> deleteCarById(@PathVariable Long id) {
        return carService.deleteCarById(id);
    }
}
