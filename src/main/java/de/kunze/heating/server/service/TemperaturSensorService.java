package de.kunze.heating.server.service;

import java.util.List;

import de.kunze.heating.server.transfer.TemperaturSensorTransfer;

public interface TemperaturSensorService {

	List<TemperaturSensorTransfer> getTemperaturSensor();

	TemperaturSensorTransfer getTemperaturSensor(Long temperaturSensorId);

	TemperaturSensorTransfer updateTemperaturSensor(Long temperaturSensorId,
			TemperaturSensorTransfer temperaturSensorTransfer);

}
