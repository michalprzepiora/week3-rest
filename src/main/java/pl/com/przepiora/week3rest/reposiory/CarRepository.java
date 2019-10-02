package pl.com.przepiora.week3rest.reposiory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.przepiora.week3rest.model.Car;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

  Optional<List<Car>> findCarByColor(String color);
}
