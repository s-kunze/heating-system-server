package de.kunze.heating.server.service;

import java.util.List;

import de.kunze.heating.server.transfer.RelaisTransfer;

public interface RelaisService {

    List<RelaisTransfer> getRelaiss();

    RelaisTransfer getRelais(Long relaisId);

    RelaisTransfer updateRelais(Long relaisId, RelaisTransfer relaisTransfer);

    RelaisTransfer on(Long relaisId);

    RelaisTransfer off(Long relaisId);

}
