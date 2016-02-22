package de.kunze.heating.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.kunze.heating.server.model.Relais;

@Repository
public interface RelaisRepository extends JpaRepository<Relais, Long> {

}
