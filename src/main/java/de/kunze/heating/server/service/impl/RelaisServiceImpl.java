package de.kunze.heating.server.service.impl;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import de.kunze.heating.server.model.Relais;
import de.kunze.heating.server.model.Status;
import de.kunze.heating.server.repository.RelaisRepository;
import de.kunze.heating.server.service.RelaisService;
import de.kunze.heating.server.transfer.RelaisTransfer;
import de.kunze.heating.server.transfer.StatusTransfer;

@Service
public class RelaisServiceImpl implements RelaisService, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RelaisRepository relaisRepository;

    @Value("${heatingsystemhost.url}")
    private String heatingSystemHostUrl;

    @Value("${heatingsystemhost.username}")
    private String username;

    @Value("${heatingsystemhost.password}")
    private String password;

    private RestTemplate heatingSystemHost = new RestTemplate();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
	HttpEntity<String> request = new HttpEntity<String>(createAuth());
	ResponseEntity<List<RelaisTransfer>> responseEntity = heatingSystemHost.exchange(
		heatingSystemHostUrl + "relais", HttpMethod.GET, request,
		new ParameterizedTypeReference<List<RelaisTransfer>>() {
		});

	if (HttpStatus.OK == responseEntity.getStatusCode()) {
	    List<RelaisTransfer> relaisTransfers = responseEntity.getBody();

	    for (RelaisTransfer relaisTransfer : relaisTransfers) {
		Relais relais = relaisRepository.findOne(relaisTransfer.getRelaisId());

		if (relais == null) {
		    relaisRepository.save(new Relais(relaisTransfer.getRelaisId(), null,
			    Status.valueOf(relaisTransfer.getStatus().name())));
		} else {
		    relais.setStatus(Status.valueOf(relaisTransfer.getStatus().name()));
		    relaisRepository.save(relais);
		}
	    }
	}
    }

    @Override
    public List<RelaisTransfer> getRelaiss() {
	return transform(relaisRepository.findAll());
    }

    @Override
    public RelaisTransfer getRelais(Long relaisId) {
	Relais relais = relaisRepository.findOne(relaisId);

	if (relais != null) {
	    return transform(relais);
	}

	return null;
    }

    @Override
    public RelaisTransfer updateRelais(Long relaisId, RelaisTransfer relaisTransfer) {
	Relais relais = relaisRepository.findOne(relaisId);

	if (relais != null) {
	    relais.setName(relaisTransfer.getName());
	    return transform(relais);
	}

	return null;
    }

    @Override
    public RelaisTransfer on(Long relaisId) {
	Relais relais = relaisRepository.findOne(relaisId);

	if (relais != null) {
	    HttpEntity<String> request = new HttpEntity<String>(createAuth());
	    ResponseEntity<Object> responseEntity = heatingSystemHost.exchange(
		    heatingSystemHostUrl + "relais/" + relaisId + "/on", HttpMethod.POST, request, Object.class);

	    if (HttpStatus.OK == responseEntity.getStatusCode()) {
		relais.setStatus(Status.ON);

		relais = relaisRepository.save(relais);
		return transform(relais);
	    }
	}

	return null;
    }

    @Override
    public RelaisTransfer off(Long relaisId) {
	Relais relais = relaisRepository.findOne(relaisId);

	if (relais != null) {
	    HttpEntity<String> request = new HttpEntity<String>(createAuth());
	    ResponseEntity<Object> responseEntity = heatingSystemHost.exchange(
		    heatingSystemHostUrl + "relais/" + relaisId + "/off", HttpMethod.POST, request, Object.class);

	    if (HttpStatus.OK == responseEntity.getStatusCode()) {
		relais.setStatus(Status.OFF);

		relais = relaisRepository.save(relais);
		return transform(relais);
	    }
	}

	return null;
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

    private List<RelaisTransfer> transform(List<Relais> relais) {
	if (relais != null) {
	    return relais.stream().map(r -> transform(r)).collect(Collectors.toList());
	}

	return new ArrayList<>();
    }

    private RelaisTransfer transform(Relais r) {
	return new RelaisTransfer(r.getId(), r.getName(), StatusTransfer.valueOf(r.getStatus().name()));
    }

}
