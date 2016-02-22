package de.kunze.heating.server.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Temperatur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double temperatur;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temperaturSensorId")
    private TemperaturSensor temperaturSensor;

}
