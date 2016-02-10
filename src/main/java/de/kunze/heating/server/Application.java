package de.kunze.heating.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.kunze.heating.server.model.TemperaturSensor;
import lombok.val;
import lombok.extern.java.Log;

@SpringBootApplication
@Log
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
		val temperaturSensor = new TemperaturSensor("Test");
	
		log.info(temperaturSensor.toString());
	}
}
