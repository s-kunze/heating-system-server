package de.kunze.heating.server.transfer;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class RelaisTransfer extends ResourceSupport {

	@JsonProperty
	private Long relaisId;

	@JsonProperty
	private String name;

	@JsonProperty
	private StatusTransfer status;

	public RelaisTransfer() {

	}

	public RelaisTransfer(Long relaisId, String name, StatusTransfer status) {
		this.relaisId = relaisId;
		this.name = name;
		this.status = status;
	}

	public Long getRelaisId() {
		return relaisId;
	}

	public void setRelaisId(Long relaisId) {
		this.relaisId = relaisId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StatusTransfer getStatus() {
		return status;
	}

	public void setStatus(StatusTransfer status) {
		this.status = status;
	}

}
