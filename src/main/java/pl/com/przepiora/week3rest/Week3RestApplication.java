package pl.com.przepiora.week3rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Week3RestApplication {

  public static void main(String[] args) {
    SpringApplication.run(Week3RestApplication.class, args);
  }

}
