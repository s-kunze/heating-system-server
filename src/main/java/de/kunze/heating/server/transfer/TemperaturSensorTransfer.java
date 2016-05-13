package de.kunze.heating.server.transfer;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class TemperaturSensorTransfer extends ResourceSupport {

	@JsonProperty
	private Long temperaturSensor;
	@JsonProperty
	private String name;
	@JsonProperty
	private String temperaturSensorId;
	@JsonProperty
	private Integer temperatur;

	public Long getTemperaturSensor() {
		return temperaturSensor;
	}

	public void setTemperaturSensor(Long temperaturSensor) {
		this.temperaturSensor = temperaturSensor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemperaturSensorId() {
		return temperaturSensorId;
	}

	public void setTemperaturSensorId(String temperaturSensorId) {
		this.temperaturSensorId = temperaturSensorId;
	}

	public Integer getTemperatur() {
		return temperatur;
	}

	public void setTemperatur(Integer temperatur) {
		this.temperatur = temperatur;
	}

}
