package de.kunze.heating.server.transfer;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class TemperaturTransfer extends ResourceSupport {

	@JsonProperty
	private Long temperaturId;

	@JsonProperty
	private Double temperatur;

	@JsonProperty
	private TemperaturSensorTransfer temperaturSensorTransfer;

	@JsonProperty
	private Long created;

	public Long getTemperaturId() {
		return temperaturId;
	}

	public void setTemperaturId(Long temperaturId) {
		this.temperaturId = temperaturId;
	}

	public Double getTemperatur() {
		return temperatur;
	}

	public void setTemperatur(Double temperatur) {
		this.temperatur = temperatur;
	}

	public TemperaturSensorTransfer getTemperaturSensorTransfer() {
		return temperaturSensorTransfer;
	}

	public void setTemperaturSensorTransfer(TemperaturSensorTransfer temperaturSensorTransfer) {
		this.temperaturSensorTransfer = temperaturSensorTransfer;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

}
