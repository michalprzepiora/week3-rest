package pl.com.przepiora.week3rest.vaadin;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.com.przepiora.week3rest.model.Car;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class RestService {
    private RestTemplate restTemplate;

    public RestService() {
        restTemplate = new RestTemplate();
    }

    public List<Car> getAllCars() {
        String url = "http://localhost:8080/cars";
        ResponseEntity<List<Car>> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Car>>() {
                });
        return response.getBody();
    }

    public void deleteCarById(Long id) throws URISyntaxException {
        String url = "http://localhost:8080/cars/" + id;
        restTemplate.delete(new URI(url));
    }

    public void addCar(Car car) throws URISyntaxException {
        String url = "http://localhost:8080/cars?color=" + car.getColor() + "&mark=" + car.getMark() + "&model=" + car.getModel();
        restTemplate.postForLocation(new URI(url), car);
    }

    public void updateCar(Long id, Car car) throws URISyntaxException {
        String url = "http://localhost:8080/cars/" + id + "?color=" + car.getColor() + "&mark=" + car.getMark() + "&model=" + car.getModel();
        restTemplate.put(new URI(url), car);
    }
}
