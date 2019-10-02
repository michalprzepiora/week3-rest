package pl.com.przepiora.week3rest.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Car> findAll(){
        return carRepository.findAll();
    }

    public Optional<Car> findById(Long id){
        return carRepository.findById(id);
    }

    public Optional<List<Car>> findCarByColor(String color){
        return carRepository.findCarByColor(color);
    }

    public void save(Car car){
        carRepository.save(car);
    }

    public void delete(Car car){
        carRepository.delete(car);
    }
}
