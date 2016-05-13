package de.kunze.heating.server.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.kunze.heating.server.service.RelaisService;
import de.kunze.heating.server.transfer.RelaisTransfer;

@RestController
@RequestMapping("/v1/relais")
public class RelaisResource {

	@Autowired
	private RelaisService relaisService;

	@RequestMapping(method = RequestMethod.GET, value = "")
	public List<RelaisTransfer> getRelaiss() {
		return relaisService.getRelaiss();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{relaisId}")
	public RelaisTransfer getRelais(@PathVariable Long relaisId) {
		return relaisService.getRelais(relaisId);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{relaisId}")
	public RelaisTransfer updateRelais(@PathVariable Long relaisId, @RequestBody RelaisTransfer relaisTransfer) {
		return relaisService.updateRelais(relaisId, relaisTransfer);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{relaisId}/on")
	public RelaisTransfer on(@PathVariable Long relaisId) {
		return relaisService.on(relaisId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{relaisId}/off")
	public RelaisTransfer off(@PathVariable Long relaisId) {
		return relaisService.off(relaisId);
	}

}
