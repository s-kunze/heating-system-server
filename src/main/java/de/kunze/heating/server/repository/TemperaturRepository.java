package de.kunze.heating.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.kunze.heating.server.model.Temperatur;
import de.kunze.heating.server.model.TemperaturSensor;

@Repository
public interface TemperaturRepository extends JpaRepository<Temperatur, Long> {

    List<Temperatur> findByTemperaturSensorOrderByCreatedDesc(TemperaturSensor temperaturSensor);

}
