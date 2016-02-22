package de.kunze.heating.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.kunze.heating.server.model.Temperatur;
import de.kunze.heating.server.model.TemperaturSensor;
import de.kunze.heating.server.repository.TemperaturRepository;
import de.kunze.heating.server.repository.TemperaturSensorRepository;
import de.kunze.heating.server.service.TemperaturSensorService;
import de.kunze.heating.server.transfer.TemperaturSensorTransfer;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TemperaturSensorServiceImpl
	implements TemperaturSensorService, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private TemperaturSensorRepository temperaturSensorRepository;

    @Autowired
    private TemperaturRepository temperaturRepository;

    @Value("${heatingsystemhost.url}")
    private String heatingSystemHostUrl;

    @Value("${heatingsystemhost.username}")
    private String username;

    @Value("${heatingsystemhost.password}")
    private String password;

    private RestTemplate heatingSystemHost = new RestTemplate();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
	log.info("Get actual temperatursensors from host");
	HttpEntity<String> request = new HttpEntity<String>(createAuth());
	ResponseEntity<List<TemperaturSensorTransfer>> responseEntity = heatingSystemHost.exchange(
		heatingSystemHostUrl + "temperatursensor", HttpMethod.GET, request,
		new ParameterizedTypeReference<List<TemperaturSensorTransfer>>() {
		});

	if (HttpStatus.OK == responseEntity.getStatusCode()) {
	    List<TemperaturSensorTransfer> temperaturSensorTransfers = responseEntity.getBody();

	    for (TemperaturSensorTransfer temperaturSensorTransfer : temperaturSensorTransfers) {
		Optional<TemperaturSensor> temperaturSensor = temperaturSensorRepository
			.findByTemperaturSensorId(temperaturSensorTransfer.getTemperaturSensorId());

		if (!temperaturSensor.isPresent()) {
		    log.info("Don't have {}", temperaturSensorTransfer.getTemperaturSensorId());
		    TemperaturSensor sensor = new TemperaturSensor();
		    sensor.setTemperaturSensorId(temperaturSensorTransfer.getTemperaturSensorId());

		    temperaturSensorRepository.save(sensor);
		}
	    }
	}
    }

    @Override
    public List<TemperaturSensorTransfer> getTemperaturSensor() {
	List<TemperaturSensor> temperaturSensors = temperaturSensorRepository.findAll();

	if (temperaturSensors != null) {
	    return temperaturSensors.stream().map(t -> transform(t)).collect(Collectors.toList());
	}

	return new ArrayList<>();
    }

    @Override
    public TemperaturSensorTransfer getTemperaturSensor(Long temperaturSensorId) {
	TemperaturSensor temperaturSensor = temperaturSensorRepository.findOne(temperaturSensorId);

	if (temperaturSensor != null) {
	    return transform(temperaturSensor);
	}

	return null;
    }

    @Override
    public TemperaturSensorTransfer updateTemperaturSensor(Long temperaturSensorId,
	    TemperaturSensorTransfer temperaturSensorTransfer) {
	TemperaturSensor temperaturSensor = temperaturSensorRepository.findOne(temperaturSensorId);

	if (temperaturSensor != null) {
	    temperaturSensor.setName(temperaturSensorTransfer.getName());

	    temperaturSensorRepository.save(temperaturSensor);

	    return transform(temperaturSensor);
	}

	return null;
    }

    @Scheduled(fixedRate = 120000, initialDelay = 1000)
    public void updateTemperatures() {
	log.info("Update temperatures");
	List<TemperaturSensor> temperaturSensors = temperaturSensorRepository.findAll();

	temperaturSensors.forEach(t -> {
	    log.info("Get Temperatur for {}", t.getId());
	    HttpEntity<String> request = new HttpEntity<String>(createAuth());
	    ResponseEntity<TemperaturSensorTransfer> responseEntity = heatingSystemHost.exchange(
		    heatingSystemHostUrl + "temperatursensor/" + t.getTemperaturSensorId(), HttpMethod.GET, request,
		    TemperaturSensorTransfer.class);

	    if (HttpStatus.OK == responseEntity.getStatusCode()) {
		TemperaturSensorTransfer temperaturSensorTransfer = responseEntity.getBody();

		Temperatur temperatur = new Temperatur();

		temperatur.setTemperatur(temperaturSensorTransfer.getTemperatur() / 1000.0d);

		log.info("Save {}°C for {}", temperatur.getTemperatur(), t.getId());
		temperatur = temperaturRepository.save(temperatur);
		temperatur.setTemperaturSensor(t);
		temperatur = temperaturRepository.save(temperatur);
	    }
	});
    }

    private TemperaturSensorTransfer transform(TemperaturSensor temperaturSensor) {
	val result = new TemperaturSensorTransfer();

	result.setTemperaturSensor(temperaturSensor.getId());
	result.setName(temperaturSensor.getName());

	return result;
    }

    private HttpHeaders createAuth() {
	String plainCreds = username + ":" + password;
	byte[] plainCredsBytes = plainCreds.getBytes();
	byte[] base64CredsBytes = Base64.encode(plainCredsBytes);
	String base64Creds = new String(base64CredsBytes);

	HttpHeaders headers = new HttpHeaders();
	headers.add("Authorization", "Basic " + base64Creds);
	return headers;
    }

}
