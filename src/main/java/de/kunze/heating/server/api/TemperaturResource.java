package de.kunze.heating.server.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.kunze.heating.server.service.TemperaturService;
import de.kunze.heating.server.transfer.TemperaturTransfer;

@RestController
@RequestMapping("/v1/temperatur")
public class TemperaturResource {

    @Autowired
    private TemperaturService temperaturService;

    @RequestMapping(method = RequestMethod.GET, value = "/{temperaturId}")
    public List<TemperaturTransfer> getTemperaturSensors(@PathVariable Long temperaturId) {
	return temperaturService.getTemperatur(temperaturId);
    }

}
