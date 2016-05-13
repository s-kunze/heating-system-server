package de.kunze.heating.server.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.kunze.heating.server.service.TemperaturSensorService;
import de.kunze.heating.server.transfer.TemperaturSensorTransfer;

@RestController
@RequestMapping("/v1/temperatursensor")
public class TemperaturSensorResource {

	@Autowired
	private TemperaturSensorService temperaturSensorService;

	@RequestMapping(method = RequestMethod.GET, value = "")
	public List<TemperaturSensorTransfer> getTemperaturSensors() {
		return temperaturSensorService.getTemperaturSensor();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{temperaturSensorId}")
	public TemperaturSensorTransfer getTemperaturSensor(@PathVariable Long temperaturSensorId) {
		return temperaturSensorService.getTemperaturSensor(temperaturSensorId);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{temperaturSensorId}")
	public TemperaturSensorTransfer updateTemperaturSensor(@PathVariable Long temperaturSensorId,
			@RequestBody TemperaturSensorTransfer temperaturSensorTransfer) {
		return temperaturSensorService.updateTemperaturSensor(temperaturSensorId, temperaturSensorTransfer);
	}

}
