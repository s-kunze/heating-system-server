package de.kunze.heating.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.kunze.heating.server.model.TemperaturSensor;

@Repository
public interface TemperaturSensorRepository extends JpaRepository<TemperaturSensor, Long> {

	Optional<TemperaturSensor> findByTemperaturSensorId(String temperaturSensorId);

}
