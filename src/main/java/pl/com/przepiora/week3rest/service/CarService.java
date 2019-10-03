package pl.com.przepiora.week3rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.com.przepiora.week3rest.model.Car;
import pl.com.przepiora.week3rest.reposiory.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

  private final CarRepository carRepository;

  @Autowired
  public CarService(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  public List<Car> findAll() {
    return carRepository.findAll();
  }

  public ResponseEntity<Car> getCarById(Long id) {
    Optional<Car> carOptional = carRepository.findById(id);
    return carOptional.map(car -> new ResponseEntity<>(car, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  public ResponseEntity<List<Car>> getCarsByColor(String color) {
    Optional<List<Car>> carsOptional = carRepository.findCarByColor(color);
    return carsOptional.map(cars -> new ResponseEntity<>(cars, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  public void addCar(String mark, String model, String color) {
    carRepository.save(Car.builder().mark(mark).model(model).color(color).build());
  }

  public ResponseEntity<Car> replaceCarById(Long id, String mark,
      String model, String color) {
    Optional<Car> carOptional = carRepository.findById(id);
    if (carOptional.isPresent()) {
      carRepository.save(Car.builder().id(id).mark(mark).model(model).color(color).build());
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<Car> replaceCarFieldById(Long id, String fieldName, String fieldValue) {
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

  public ResponseEntity<Car> deleteCarById(Long id) {
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
    carRepository.save(Car.builder().mark("Fiat").model("Uno").color("white").build());
    carRepository.save(Car.builder().mark("VW").model("Golf").color("pink").build());
    carRepository.save(Car.builder().mark("Tesla").model("S").color("black").build());
  }
}
