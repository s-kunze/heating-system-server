package de.kunze.heating.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.kunze.heating.server.model.Temperatur;
import de.kunze.heating.server.model.TemperaturSensor;
import de.kunze.heating.server.repository.TemperaturRepository;
import de.kunze.heating.server.repository.TemperaturSensorRepository;
import de.kunze.heating.server.service.TemperaturService;
import de.kunze.heating.server.transfer.TemperaturSensorTransfer;
import de.kunze.heating.server.transfer.TemperaturTransfer;
import lombok.val;

@Service
public class TemperaturServiceImpl implements TemperaturService {

	@Autowired
	private TemperaturSensorRepository temperaturSensorRepository;

	@Autowired
	private TemperaturRepository temperaturRepository;

	@Override
	public List<TemperaturTransfer> getTemperatur(Long temperaturId) {
		TemperaturSensor temperaturSensor = temperaturSensorRepository.findOne(temperaturId);

		if (temperaturSensor != null) {
			List<Temperatur> temperatures = temperaturRepository
					.findByTemperaturSensorOrderByCreatedDesc(temperaturSensor);

			if (temperatures != null) {
				return temperatures.stream().map(t -> transform(t)).collect(Collectors.toList());
			}
		}

		return new ArrayList<>();
	}

	private TemperaturTransfer transform(Temperatur t) {
		val temperaturSensorTransfer = new TemperaturSensorTransfer();
		temperaturSensorTransfer.setName(t.getTemperaturSensor().getName());

		val result = new TemperaturTransfer();

		result.setCreated(t.getCreated().getTime());
		result.setTemperatur(t.getTemperatur());
		result.setTemperaturId(t.getId());
		result.setTemperaturSensorTransfer(temperaturSensorTransfer);

		return result;
	}

}
