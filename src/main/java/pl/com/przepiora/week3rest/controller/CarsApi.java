package pl.com.przepiora.week3rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
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
import pl.com.przepiora.week3rest.reposiory.CarRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarsApi {

  private CarRepository carRepository;

  @Autowired
  public CarsApi(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  @GetMapping
  public List<Car> getAllCars() {
    return carRepository.findAll();
  }

  @GetMapping("{id}")
  public ResponseEntity<Car> getCarById(@PathVariable Long id) {
    Optional<Car> carOptional = carRepository.findById(id);
    return carOptional.map(car -> new ResponseEntity<>(car, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping("/color/{color}")
  public ResponseEntity<List<Car>> getCarsByColor(@PathVariable String color) {
    Optional<List<Car>> carsOptional = carRepository.findCarByColor(color);
    return carsOptional.map(cars -> new ResponseEntity<>(cars, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public void addCar(@RequestParam String mark,
      @RequestParam String model, @RequestParam String color) {
    carRepository.save(Car.builder().mark(mark).model(model).color(color).build());
  }

  @PutMapping("{id}")
  public ResponseEntity<Car> replaceCarById(@PathVariable Long id, @RequestParam String mark,
      @RequestParam String model, @RequestParam String color) {
    Optional<Car> carOptional = carRepository.findById(id);
    if (carOptional.isPresent()) {
      carRepository.save(Car.builder().id(id).mark(mark).model(model).color(color).build());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @PatchMapping("{id}")
  public ResponseEntity<Car> replaceCarFieldById(@PathVariable Long id,
      @RequestParam String fieldName, @RequestParam String fieldValue) {
    Optional<Car> carOptional = carRepository.findById(id);
    Car car;
    if (carOptional.isPresent()) {
      car = carOptional.get();
      switch (fieldName) {
        case "mark":
          car.setMark(fieldValue);
          break;
        case "model":
          car.setModel(fieldValue);
          break;
        case "color":
          car.setColor(fieldValue);
          break;
        default:
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
      carRepository.save(car);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Car> deleteCarById(@PathVariable Long id) {
    Optional<Car> carOptional = carRepository.findById(id);
    if (carOptional.isPresent()) {
      carRepository.delete(carOptional.get());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new
        ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void addThreeCarsToRepo() {
    carRepository.save(Car.builder().mark("Fiat").model("Uno").color("White").build());
    carRepository.save(Car.builder().mark("VW").model("Golf").color("Pink").build());
    carRepository.save(Car.builder().mark("Tesla").model("S").color("Black").build());
  }
}
