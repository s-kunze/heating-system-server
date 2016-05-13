package de.kunze.heating.server.service;

import java.util.List;

import de.kunze.heating.server.transfer.TemperaturTransfer;

public interface TemperaturService {

	List<TemperaturTransfer> getTemperatur(Long temperaturId);

}
