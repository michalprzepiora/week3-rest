package pl.com.przepiora.week3rest.vaadin;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.com.przepiora.week3rest.model.Car;

import java.util.List;

public class RestService {
  private RestTemplate restTemplate;
  final static String GET_ALL_CARS =  "http://localhost:8080/cars";

  public RestService() {
    restTemplate = new RestTemplate();
  }

  public List<Car> getAllCars(){
    ResponseEntity<List<Car>> response = restTemplate.exchange(GET_ALL_CARS, HttpMethod.GET, null,
        new ParameterizedTypeReference<List<Car>>() {});
    return response.getBody();
  }
}
